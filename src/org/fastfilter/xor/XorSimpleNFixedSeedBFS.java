package org.fastfilter.xor;

//import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import org.fastfilter.Filter;
import org.fastfilter.utils.Hash;

/*import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;*/
/**
 * Class with the simple XOR filter based on the one from fastfilter library
 * @author Alfonso
 *
 */
public class XorSimpleNFixedSeedBFS implements Filter {

	/** Seed for the hash functions used in the filters */
    private long seed;
    /** stored fingerprints */
    private long[] data;
    /** lenght of the blocks used for each of the three hash function */
    int blockLength;
    /** Default total number of bits per fingerprint (r)*/
    int bitsPerFingerprint=8;
    /** Default mask to check the bits (bitsN)*/
    long mask = 0xFF;
    
    private static final long prime = 0xB1996F925521C047L;
    
    private int sizeSet = 0;
    private long lastPos = 0;
    private long firstPos = 0;
    private boolean usedDefaultSeed = true;
    
    private static final double FACTOR = 1.23d;//1.226d;//1.3d;

    //private HashFunction hf;


	/** Maximum number of iterations to converge */
    private static final int MAX_ITERATIONS= 30;
    
	/** Number of iterations to converge */
	private int tries;
	private boolean useKnuthMultHash;

    
    public boolean isUsedDefaultSeed() {
		return usedDefaultSeed;
	}
    
    /** Number of total bits stored in the filter */
    public long getBitCount() {
        return data.length * bitsPerFingerprint;
    }

    /** Method to construct the filter
     * @param keys Set S with the elements that are added to the filter 
     * @param bitsPerFingerprint Number of bits for each entry of the filter
     * @param indep 
     * @param randInit 
     * @return a new Filter with the selected parameters
     */
    public static XorSimpleNFixedSeedBFS construct(Set<Long> keys, int bitsPerFingerprint, long seed, boolean randInit, boolean indep) {
        return new XorSimpleNFixedSeedBFS (keys, bitsPerFingerprint, seed, randInit, indep);
    }

    /** Method to construct the filter
     * @param keys Set S with the elements that are added to the filter 
     * @param bitsPerFingerprint Number of bits for each entry of the filter
     * @param tSize If a specific size different from the calculated one is preferred
     * @return a new Filter with the selected parameters
     */
    public static XorSimpleNFixedSeedBFS construct(Set<Long> keys, int bitsPerFingerprint, int tSize, long seed, boolean randInit, boolean indep) {
        return new XorSimpleNFixedSeedBFS (keys, bitsPerFingerprint, tSize, seed, randInit, indep);
    }

    /** 
     * @param keys Set S with the elements that are added to the filter 
     * @param bitsPerFingerprint Number of bits for each entry of the filter
     * @return a new Filter with the selected parameters
     */
    XorSimpleNFixedSeedBFS(Set<Long> keys, int bitsPerFingerprint, long seedInit, boolean randInit, boolean indep) {
    	//this(keys, bitsPerFingerprint, (int) ((1.23 * keys.size()) + 32), seedInit, randInit, indep);
    	this(keys, bitsPerFingerprint, (int) ((FACTOR * keys.size()) + 32), seedInit, randInit, indep);
    }
    
    /** Method to construct the filter
     * @param keys Set S with the elements that are added to the filter 
     * @param bitsPerFingerprint Number of bits for each entry of the filter
     * @param tSize If a specific size different from the calculated one is preferred
     * @return a new Filter with the selected parameters
     */
    XorSimpleNFixedSeedBFS(Set<Long> keys, int bitsPerFingerprint, int tsize, long seedInit, boolean randInit, boolean indep) {
    	this.sizeSet=keys.size();
    	// With this implementation we can only have a maximum of 64 bits as we are
    	// using longs to store the information
    	if (bitsPerFingerprint<=64 && bitsPerFingerprint>0) {
    		this.bitsPerFingerprint = bitsPerFingerprint;
    		this.mask = (long)(Math.pow(2, bitsPerFingerprint)-1);
    	}
    	// Each block for the hash functions correspond to 1/3 of the total size
        blockLength = tsize/3;
        
        //hf = Hashing.murmur3_128((int) seedInit); 
        // Recreate data
    	
        if (randInit) {
        	Random r = new Random(seedInit);
        	data = r.longs(3*blockLength).toArray();
        }else {
        	data = new long[3 * blockLength];
        }
        this.useKnuthMultHash = indep; 
    	
    	seed=seedInit;
    	/* Populating the filter */    
        while (true) {
            tries++;
        	// Seed for the hash functions
            if(tries>1) {
            	seed = new Random().nextLong();
            	this.usedDefaultSeed=false;
            }
            // Stack that will hold the pair element hash, selected position
            long[] stack = new long[keys.size() * 2];
            // Try to create a map for each s of S to a position i and add it to the stack
            if (map(keys, seed, stack)) {
            	// If it was possible to create the map, assign value to the
            	// position so the fingerprint is equal to the xors of the hash positions 
                assign(stack, data);
            	//System.out.println(Arrays.toString(data));
                printExtendedData();
                return;
            }
            // Limit the maximum number of tries. If it fails, the program aborts.
            if(tries>MAX_ITERATIONS) {
            	System.out.println("Could not map the elements to unique positions in the filter");
            	System.exit(0); //TODO: change to the appropriate behavior.
            }
        }

    }

	/**
	 * Try to create a map for each s of S to a position i and add it to the stack
	 * @param keys elements to be added to the filter and should return a positive
	 * @param seed Seed for the hash functions
	 * @param stack Stack where each element will be mapped to a unique position
	 * @return true if the map could be done, false otherwise.
	 */

    boolean map(Set<Long> keys, long seed, long[] stack) {
    	// C and H will store the number of elements mapped to a hash and the corresponding h position
        int[] C = new int[3 * blockLength];
        long[] H = new long[3 * blockLength];
        // For each element in S
        for (long k : keys) {
        	// Calculate the base hash for 64 bits
            long x = Hash.hash64(k, seed);
            // Calculate the three hash functions ==> positions and add the values to C and H
            for (int j = 0; j < 3; j++) {
                int index = h(x, j);
                C[index]++;
                H[index] ^= x;
            }
        }
        // Array that stores the positions with just one element
        int[] Q = new int[3 * blockLength];
        // next position to be filled
        int qi = 0;
        // Look for positions with only 1 element assigned
        for (int i = 0; i < C.length; i++) {
            if (C[i] == 1) {
            	// Add those with just one element to Q
                Q[qi++] = i;
            }
        }
        // next position to be read from Q
        int qf = 0;
        // index for next position to be used in the stack
        int si = 0;
        // Two values are stored per element, the element itself and the unique position assigned
        while (si < 2 * keys.size()) {
        	// It was not possible to cover all elements. At this point all the remaining
        	// positions have, at least, 2 or more elements.
        	//if(qi==0) {
        	if(qi<=qf) {
        		return false;
        	}
        	// Take the next element from the stack
            int i = Q[qf++];
        	//int i = Q[--qi];
            // If that position is only assigned to 1 element
            if (C[i] == 1) {
            	// Get the 64-bit hash from that position
                long x = H[i];
                // Assign the hash to the position including both in the stack 
                stack[si++] = x;
                stack[si++] = i;
                // Remove the element from every position
                for (int j = 0; j < 3; j++) {
                    int index = h(x, j);
                    C[index]--;
                    // If, after removal, one of the positions is only assigned to 1 element 
                    if (C[index] == 1) {
                    	// Add the position to the stack
                        Q[qi++] = index;
                    }
                    H[index] ^= x;
                }
            }
        }
        // Have all the elements a unique position assigned
        return si == 2 * keys.size();
    }

    /**
     * Store the appropriate values into each position to get the correct positives 
     * @param stack The stack with the information of the elements (64-bit hash) and the unique position
     * @param b The data array where the elements are to be stored
     */
    void assign(long[] stack, long[] b) {
    	// Traverse the stack
        for(int stackPos = stack.length; stackPos > 0;) {
        	// Get the position assigned
            int index = (int) stack[--stackPos];
            // Get the element (its 64-bit hash) assigned to that position
            long x = stack[--stackPos];
            //printAssign(index, x);
            // Include the XOR of the three positions and the stored value in the selected
            // position for the bits corresponding to the mask.
            b[index]=0;
            b[index] = (fingerprint(x) ^ b[h(x, 0)] ^ b[h(x, 1)] ^ b[h(x, 2)]);
            
        }
        lastPos = stack[1];
        firstPos = stack[stack.length-1];
    }

    void printAssign(int index, long x) {
       System.out.println(index+";"+x);  	
    }
    /**
     * Calculate one of the positions corresponding to an element based on the hash
     * @param x 64-bit hash of the element
     * @param index Which of the n hash positions to be retrieved.
     * @return The position in the array
     */
    int h(long x, int index) {
        return Hash.reduce((int) Long.rotateLeft(x, index * 21), blockLength) + index * blockLength;
    }

    /**
     * Check if the key is in the filter
     * @param key Key to be checked
     * @return if it gives a positive
     */
    @Override
    public boolean mayContain(long key) {
        long x = Hash.hash64(key, seed);
        return fingerprint(x) == ((data[h(x, 0)] ^ data[h(x, 1)] ^ data[h(x, 2)]) & mask);
    }
    
    public long testFP(long key) {
    	long x = Hash.hash64(key, seed);
        return fingerprint(x);
    }

    public long posh(long key, int index) {
    	long x = Hash.hash64(key, seed);
    	return h(x, index);
    }
    
    // Generate the fingerprint for a specific mask
    private long fingerprint(long x) {
    	if (useKnuthMultHash) {
    		long y = prime*x;
    		return  (y>>>(64-bitsPerFingerprint));
    	}
        return x & mask;
    }

    /**
     * Internal iterations to converge
     * @return
     */
    public int getTries() {
		return tries;
	}

    /**
     * Print data
     */
    public void printData() {
    	System.out.println(firstPos+";"+lastPos+";"+data[(int)lastPos]+";"+blockLength+";"+sizeSet);
    }


    /**
     * Print extendeddata
     */
    public void printExtendedData() {
    	/*int zeros = 0;
    	int zerosAssigned = 0;
    	for (int i=0; i<data.length; i++) {
    		if(data[i]==0) {

    			zeros++;
    		}
    	}
    	System.out.println("ZEROS: "+zeros+" de los cuales asignados/no asignados: " +zerosAssigned + "/"+(zeros-zerosAssigned));*/
    }
}
