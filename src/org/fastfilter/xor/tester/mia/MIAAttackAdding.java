package org.fastfilter.xor.tester.mia;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.fastfilter.xor.XorSimpleNFixedSeed;

public class MIAAttackAdding {
	
	public static void main (String[] args) {
		if (args.length!=5){
			System.out.println("Incorrect number of arguments");
			System.exit(1);
		}
		int iterations=1000;
		int fingerbits=2;
		int rebuiltTarget=4;
		int elementsToAdd=1;
		int sSize=65536;
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
		MIAAttackAdding.run(iterations, fingerbits, rebuiltTarget, elementsToAdd, sSize);
	}
	
	public static void run (int iterations, int fingerbits, int rebuiltTarget, int elementsToAdd, int sSize) {
		int max = 0;
		int current = 0;
		int mSize=134217728;
		/*System.out.print("%sSize;mSize;r;t;e");
		for (int j=1;j<=rebuiltTarget;j++) {
			System.out.print(";n"+j);
		}
		System.out.println();*/
		for (int i=0; i<iterations;i++) {
			current=MIAAttackAdding.check(fingerbits, sSize, mSize, rebuiltTarget, elementsToAdd);
			max=(current>max)?current:max;
		}
		//System.out.println("Mean: " + total/(double)iterations);
		//System.out.println("Max: " + max);
	}

	private static int check(int fingerbits, int sSize, int mSize, int rebuiltTarget, int elementsToAdd) {
		
		Set<Long> positives = MIAAttackAdding.build(sSize, null);
		Set<Long> helper = new HashSet<Long>(positives);
		Set<Long> fp = new HashSet<Long>();

		
		int[] nt = new int[rebuiltTarget];
		boolean[] seedDef = new boolean[rebuiltTarget];
		
		long seed = 12341234;
		XorSimpleNFixedSeed filter = XorSimpleNFixedSeed.construct(positives, fingerbits, seed, true, false);
		
				
		MIAAttackAdding.checkPositives(filter, helper);
		
		Random r =  new Random();		
		
		for (int exec=0; exec<mSize; exec++) {
			Long x = MIAAttackAdding.buildLong(positives, r);
			if (filter.mayContain(x)) {
				fp.add(x);
			}
		}
		
		nt[0] = fp.size();
		seedDef[0] = filter.isUsedDefaultSeed();
		//filter.printData();
		
		int i = 0;

		while(i<rebuiltTarget-1) {
			i++;
			for (int j=0; j<elementsToAdd;j++) {
				helper.add(MIAAttackAdding.buildLong(helper, null));
			}
			filter = XorSimpleNFixedSeed.construct(helper, fingerbits, seed, true, false);
			
			for (Iterator<Long> it = fp.iterator(); it.hasNext();) {
				Long fpl = it.next();
				if(!filter.mayContain(fpl)) {
					it.remove();
				}
			}
			//filter.printData();
			nt[i]=fp.size();
			seedDef[i] = filter.isUsedDefaultSeed();
	   }

	   System.out.print(sSize+";"+mSize+";"+fingerbits+";"+rebuiltTarget+";"+elementsToAdd);
	   for (int j=0;j<rebuiltTarget;j++) {
		   System.out.print(";"+nt[j]);
	   }
	   for (int j=0;j<rebuiltTarget;j++) {
		   System.out.print(";"+((seedDef[j])?"t":"f"));
	   }
	   System.out.println();
	   return nt[rebuiltTarget-1];
	}

	private static boolean checkPositives(XorSimpleNFixedSeed filter, Set<Long> helper) {
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
	public static Set<Long> build (int elements, Set<Long> exclude) {
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
	
	public static Long buildLong(Set<Long> exclude, Random random) {
		if (random==null) {
			random = new Random();
		}
		long next = -1;
		
		do {
			next = random.nextLong();
		}while (exclude!=null && exclude.contains(next));
		
		return next;
			
	}


}
