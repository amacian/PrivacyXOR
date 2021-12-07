package org.fastfilter.xor.tester.mia;

import java.util.Random;

public class MIASecondExperiment {
	public static void main (String[] args) {
		int iterations=1;
		int fingerbits=6;
		int rebuiltTarget=15;
		int sSize=65536;
		if (args.length==1){
			sSize=Integer.parseInt(args[0]);
		}
		Random rand=new Random();
		
		
		for (int i=0;i<1000;i++) {
			int elementsToAdd = rand.nextInt(509)+4;
			MIAAttackAdding.run(iterations, fingerbits, rebuiltTarget, elementsToAdd, sSize);
		}
	}
}
