# PrivacyXOR
This repository includes the implementation in Java of Xor Filter Privacy Attacks for the paper:

P. Reviriego, A. Sánchez-Macián, P.C. Dillinger and S. Walzer, "On the Privacy of Approximate Membership Check Filters under Persistent Attacks", under submission to IEEE Transactions on Dependable and Secure Computing (TDSC).

# Dependencies
- Fastfilter Java implementation: https://github.com/FastFilter/fastfilter_java/

# Content

*src* directory includes the following files:
Classes in the package org.fastfilter.xor:
- XorSimpleNFixedSeed.java (implementation of the original xor filter using a variable number N of bits for the fingerprint, and a fixed default seed)
- XorSimpleNFixedSeedBFS.java (implementation of a FIFO-construction based xor filter using a variable number N of bits for the fingerprint, and a fixed default seed)

Classes in the package org.fastfilter.xor.tester.mia:
- MIAAttackAdding.java (implementation of periodic addition of a fixed number of elements and reconstruction of the filter to verify which information is leaked - first experiment).
- MIASecondExperiment.java (implementation of periodic addition of a rundom number of elements and reconstruction of the filter to verify which information is leaked - second experiment).
- MIAAttackReplacing.java (implementation of periodic replacement of a fixed number of elements and reconstruction of the filter to verify which information is leaked - third experiment).

# Execution of the code
Compile the java classes and resolve dependencies. Then:
## First experiment

Execute org.fastfilter.xor.tester.mia.MIAAttackAdding to generate the log files that provide the results for the first experiment.

`java -cp <classpath> org.fastfilter.xor.tester.mia.MIAAttackAdding <fingerprint_bits> <times_to_rebuild_filter> <elements_to_add> <iterations> <size_of_S_set>`

For instance:

`java -cp ./bin:../fastfilter_java/bin org.fastfilter.xor.tester.mia.MIAAttackAdding 6 15 1 200 65536`

Will create the XOR filter with a fingerprint of 6 bits and 65536 elements. It will add 1 element every time. Filter will be built 15 times (the initial and after adding 1 element 14 additional times). This experiment will be executed 200 times to get the mean values.

## Second experiment

Execute org.fastfilter.xor.tester.mia.MIASecondExperiment to generate the log files that provide the results for the second experiment.

`java -cp <classpath> org.fastfilter.xor.tester.mia.MIASecondExperiment`

For instance:

`java -cp ./bin:../fastfilter_java/bin org.fastfilter.xor.tester.mia.MIASecondExperiment`

## Third experiment

Execute org.fastfilter.xor.tester.mia.MIAAttackReplacing to generate the log files that provide the results for the first experiment.

`java -cp <classpath> org.fastfilter.xor.tester.mia.MIAAttackReplacing <fingerprint_bits> <times_to_rebuild_filter> <elements_to_add> <iterations> <size_of_S_set> <fifo_lifo> <zero_random> <none_indep>`

For instance:

`java -cp ./bin:../fastfilter_java/bin org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1 200 65536 LIFO ZERO NONE`

Will create the XOR filter with a fingerprint of 6 bits and 65536 elements, using the original implementation approach (LIFO, initializing the contents to zero and default fingerprint). It will replace 1 element every time. Filter will be built 15 times (the initial and after replacing 1 element 14 additional times). This experiment will be executed 200 times to get the mean values.

`java -cp ./bin:../fastfilter_java/bin org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1 200 65536 FIFO RAND INDEP`

Will create the XOR filter with a fingerprint of 6 bits and 65536 elements, using a FIFO-approach, initializing the contents to random values and a fingerprint based on Knuth multiplicative hashing. It will replace 1 element every time. Filter will be built 15 times (the initial and after replacing 1 element 14 additional times). This experiment will be executed 200 times to get the mean values.

