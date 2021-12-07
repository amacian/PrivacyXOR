package org.fastfilter.xor.tester.mia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


import org.fastfilter.xor.XorSimpleNFixedSeed;

public class MIAAttackReplacing {
	
	public static void main (String[] args) {
		if (args.length!=5){
			System.out.println("Incorrect number of arguments");
			System.exit(1);
		}
		int iterations=1000;
		int fingerbits=2;
		int rebuiltTarget=5;
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
		MIAAttackReplacing.run(iterations, fingerbits, rebuiltTarget, elementsToAdd, sSize);
	}
	
	public static void run (int iterations, int fingerbits, int rebuiltTarget, int elementsToAdd, int sSize) {
		int total = 0;
		int max = 0;
		int current = 0;
		int mSize=134217728;
		System.out.print("%sSize;mSize;r;t;e");
		for (int j=1;j<=rebuiltTarget;j++) {
			System.out.print(";n"+j);
		}
		for (int j=1;j<=rebuiltTarget;j++) {
			System.out.print(";def_seed"+j);
		}
		System.out.println();
		for (int i=0; i<iterations;i++) {
			current=MIAAttackReplacing.check(fingerbits, sSize, mSize, rebuiltTarget, elementsToAdd);
			total+=current;
			max=(current>max)?current:max;
		}
		System.out.println("Mean: " + total/(double)iterations);
		System.out.println("Max: " + max);
	}

	private static int check(int fingerbits, int sSize, int mSize, int rebuiltTarget, int elementsToAdd) {
		
		Set<Long> positives = MIAAttackReplacing.build(sSize, null);
		Set<Long> helper = new HashSet<Long>(positives);
		Set<Long> fp = new HashSet<Long>();

		
		int[] nt = new int[rebuiltTarget];
		boolean[] seedDef = new boolean[rebuiltTarget];
		
		long seed=12341234;
		//XorSimpleNFixedSeed filter = XorSimpleNFixedSeed.construct(positives, fingerbits, seed);
		XorSimpleNFixedSeed filter = XorSimpleNFixedSeed.construct(helper, fingerbits, seed);
		
		MIAAttackReplacing.checkPositives(filter, helper);
		
		Random r =  new Random();		
		
		for (int exec=0; exec<mSize; exec++) {
			Long x = MIAAttackReplacing.buildLong(positives, r);
			if (filter.mayContain(x)) {
				fp.add(x);
			}
		}
		//filter.printData();
		nt[0] = fp.size();
		seedDef[0] = filter.isUsedDefaultSeed();
		
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
			filter = XorSimpleNFixedSeed.construct(helper, fingerbits, seed);
			//filter.printData();
			for (Iterator<Long> it = fp.iterator(); it.hasNext();) {
				Long fpl = it.next();
				if(!filter.mayContain(fpl)) {
					it.remove();
				}
			}
			
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
