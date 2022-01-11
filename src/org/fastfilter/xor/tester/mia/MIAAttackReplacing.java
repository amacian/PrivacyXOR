package org.fastfilter.xor.tester.mia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.fastfilter.Filter;
import org.fastfilter.xor.XorSimpleNFixedSeed;
import org.fastfilter.xor.XorSimpleNFixedSeedBFS;

public class MIAAttackReplacing {
	
	public static void main (String[] args) {
		if (args.length!=8){
			System.out.println("Incorrect number of arguments");
			System.exit(1);
		}
		int iterations=1000;
		int fingerbits=2;
		int rebuiltTarget=5;
		int elementsToAdd=1;
		int sSize=65536;
		boolean lifo=true;
		boolean randInit=false;
		boolean indep=false;
		try{
			fingerbits = Integer.parseInt(args[0]);
			rebuiltTarget = Integer.parseInt(args[1]);
			elementsToAdd = Integer.parseInt(args[2]);
			iterations = Integer.parseInt(args[3]);
			sSize = Integer.parseInt(args[4]);
		}catch (NumberFormatException nfe){
			System.out.println("Error in the format of the arguments");
			System.exit(1);
		}
		if(args[5].equals("FIFO")) {
			lifo=false;
		}
		if(args[6].equals("RAND")) {
			randInit=true;
		}
		if(args[7].equals("INDEP")) {
			indep=true;
		}
		MIAAttackReplacing.run(iterations, fingerbits, rebuiltTarget, elementsToAdd, sSize, lifo, randInit, indep);
	}
	
	public static void run (int iterations, int fingerbits, int rebuiltTarget, int elementsToAdd, int sSize, boolean lifo, boolean randInit, boolean indep) {
		int total = 0;
		int max = 0;
		int current = 0;
		int mSize=134217728;
		System.out.print("%sSize;mSize;r;t;e;queue;init;indep");
		for (int j=1;j<=rebuiltTarget;j++) {
			System.out.print(";n"+j);
		}
		for (int j=1;j<=rebuiltTarget;j++) {
			System.out.print(";def_seed"+j);
		}
		System.out.println();
		long seed=12341234;
		Random r =  new Random(seed);	
		for (int i=0; i<iterations;i++) {
			current=MIAAttackReplacing.check(fingerbits, sSize, mSize, rebuiltTarget, elementsToAdd, r, seed, lifo, randInit, indep);
			total+=current;
			max=(current>max)?current:max;
		}
		System.out.println("Mean: " + total/(double)iterations);
		System.out.println("Max: " + max);
	}

	private static int check(int fingerbits, int sSize, int mSize, int rebuiltTarget, int elementsToAdd, Random r, long seed, boolean lifo, boolean randInit, boolean indep) {
		
		Set<Long> positives = MIAAttackReplacing.build(sSize, null);
		Set<Long> helper = new HashSet<Long>(positives);
		Set<Long> fp = new HashSet<Long>();

		
		int[] nt = new int[rebuiltTarget];
		boolean[] seedDef = new boolean[rebuiltTarget];

		
		//XorSimpleNFixedSeed filter = XorSimpleNFixedSeed.construct(positives, fingerbits, seed);
		//XorSimpleNFixedSeedBFS filter = XorSimpleNFixedSeedBFS.construct(helper, fingerbits, seed);
		Filter filter = MIAAttackReplacing.buildFilter(helper, fingerbits, seed, lifo, randInit, indep);
		
		MIAAttackReplacing.checkPositives(filter, helper);
		
	
		
		for (int exec=0; exec<mSize; exec++) {
			Long x = MIAAttackReplacing.buildLong(positives, r);
			if (filter.mayContain(x)) {
				fp.add(x);
			}
		}
		nt[0] = fp.size();
		seedDef[0] = MIAAttackReplacing.isUsedDefaultSeed(filter, lifo);
		
		int i = 0;

		while(i<rebuiltTarget-1) {
			i++;
			ArrayList<Long> list = new ArrayList<Long>(helper);
			
			for (int j=0; j<elementsToAdd;j++) {
				list.remove(r.nextInt(list.size()));
			}
			for (int j=0; j<elementsToAdd;j++) {
				list.add(MIAAttackReplacing.buildLong(helper, null));
			}
			helper = new HashSet<Long>(list);
			filter = MIAAttackReplacing.buildFilter(helper, fingerbits, seed, lifo, randInit, indep);
			//XorSimpleNFixedSeedBFS.construct(helper, fingerbits, seed);
			//filter = XorSimpleNFixedSeed.construct(helper, fingerbits, seed);
			for (Iterator<Long> it = fp.iterator(); it.hasNext();) {
				Long fpl = it.next();
				if(!filter.mayContain(fpl)) {
					it.remove();
				}
			}
			/*int tot=0;
			for (Iterator<Long> it = fp.iterator(); it.hasNext();) {
				Long fpl = it.next();
				if(filter.testFP(fpl)==0) {
					tot++;
				}
			}
			System.out.println(tot);*/
			
			nt[i]=fp.size();
			seedDef[i] = MIAAttackReplacing.isUsedDefaultSeed(filter, lifo);
	   }

	   System.out.print(sSize+";"+mSize+";"+fingerbits+";"+rebuiltTarget+";"+elementsToAdd+";"+((lifo)?"LIFO":"FIFO")+";"+((randInit)?"RAND":"ZERO") +";"+((indep)?"INDEP":"ORIG"));
	   for (int j=0;j<rebuiltTarget;j++) {
		   System.out.print(";"+nt[j]);
	   }
	   for (int j=0;j<rebuiltTarget;j++) {
		   System.out.print(";"+((seedDef[j])?"t":"f"));
	   }
	   System.out.println();
		/*for (Iterator<Long> it = fp.iterator(); it.hasNext();) {
			Long fpl = it.next();
			System.out.println(filter.testFP(fpl));
		}*/
	   return nt[rebuiltTarget-1];
	}

	/**
	 * 
	 * @return
	 */
	private static boolean isUsedDefaultSeed(Filter filter, boolean lifo) {
		return (lifo)?((XorSimpleNFixedSeed)filter).isUsedDefaultSeed():
			((XorSimpleNFixedSeedBFS)filter).isUsedDefaultSeed();
	}

	private static boolean checkPositives(Filter filter, Set<Long> helper) {
	//private static boolean checkPositives(XorSimpleNFixedSeed filter, Set<Long> helper) {
		for (Long l: helper) {
			if(!filter.mayContain(l)) {
				System.out.println("Error in validation");
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to build the sets
	 * @param elements number of elements (longs) to be generated
	 * @param exclude Set with elements that should not be included in the new Set
	 * @return the generated Set with the "long" elements.
	 */
	private static Set<Long> build (int elements, Set<Long> exclude) {
		Set<Long> s = new HashSet<Long>();

		// Generate the elements pseudorandomly
		Random random = new Random();

		// Until all the elements have been generated
		for (int i=0; i< elements; i++) {
			// Generate a long, check that is not in the excluded set and add it to the new set
			// If it is in the excluded set, repeat the operation
			do{
				long next = random.nextLong();
				if(exclude==null || !exclude.contains(next)) {
					s.add(next);
				}
			}while(s.size()<i+1);
		}
		return s;
	}
	
	/**
	 * Build a random long not included in the "exclude" set.
	 * @param exclude set of elements to be excluded in the creation
	 * @param random Random object to be used or create a new one if null
	 * @return a random long
	 */
	private static Long buildLong(Set<Long> exclude, Random random) {
		if (random==null) {
			random = new Random();
		}
		long next = -1;
		
		do {
			next = random.nextLong();
		}while (exclude!=null && exclude.contains(next));
		
		return next;
			
	}
	
	/**
	 * Build a filter to be used in the experiments
	 * @param helper Positives to be included in the XOR filter
	 * @param fingerbits Number of bits for the fingerprint
	 * @param seed Random seed to be used by default
	 * @param lifo Build a XOR that assigns based on a LIFO or a FIFO
	 * @param randInit Initialize the xor with 0s or random values 
	 * @param indep Use an additional element to make fingerprint and positions independent.
	 * @return
	 */
	private static Filter buildFilter(Set<Long> helper, int fingerbits, long seed, boolean lifo, boolean randInit, boolean indep) {
		return (lifo)?XorSimpleNFixedSeed.construct(helper, fingerbits, seed, randInit, indep):
			XorSimpleNFixedSeedBFS.construct(helper, fingerbits, seed, randInit, indep);
	}
	
	


}
