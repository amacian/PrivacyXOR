ECHO Start of Loop

set GUAVA_DIR=<PATH_TO>/guava-30.1.1-jre.jar
set FASTFILTER_DIR=<PATH_TO>/fastfilter_java/bin

set CLASSPATH=./bin;%GUAVA_DIR%;%FASTFILTER_DIR%

java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  FIFO ZERO NONE > FZN6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  LIFO ZERO NONE > LZN6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  LIFO RAND NONE > LRN6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  FIFO RAND NONE > FRN6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  LIFO ZERO INDEP > LZI6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  FIFO ZERO INDEP > FZI6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  LIFO RAND INDEP > LRI6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6 100 65536  FIFO RAND INDEP > FRI6.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  FIFO ZERO NONE > FZN64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  LIFO ZERO NONE > LZN64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  LIFO RAND NONE > LRN64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  FIFO RAND NONE > FRN64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  LIFO ZERO INDEP > LZI64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  FIFO ZERO INDEP > FZI64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  LIFO RAND INDEP > LRI64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 64 100 65536  FIFO RAND INDEP > FRI64.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  FIFO ZERO NONE > FZN640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  LIFO ZERO NONE > LZN640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  LIFO RAND NONE > LRN640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  FIFO RAND NONE > FRN640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  LIFO ZERO INDEP > LZI640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  FIFO ZERO INDEP > FZI640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  LIFO RAND INDEP > LRI640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 640 100 65536  FIFO RAND INDEP > FRI640.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  FIFO ZERO NONE > FZN6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  LIFO ZERO NONE > LZN6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  LIFO RAND NONE > LRN6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  FIFO RAND NONE > FRN6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  LIFO ZERO INDEP > LZI6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  FIFO ZERO INDEP > FZI6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  LIFO RAND INDEP > LRI6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 6400 100 65536  FIFO RAND INDEP > FRI6400.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  FIFO ZERO NONE > FZN105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  LIFO ZERO NONE > LZN105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  LIFO RAND NONE > LRN105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  FIFO RAND NONE > FRN105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  LIFO ZERO INDEP > LZI105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  FIFO ZERO INDEP > FZI105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  LIFO RAND INDEP > LRI105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 105 100 1048576  FIFO RAND INDEP > FRI105.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  FIFO ZERO NONE > FZN1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  LIFO ZERO NONE > LZN1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  LIFO RAND NONE > LRN1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  FIFO RAND NONE > FRN1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  LIFO ZERO INDEP > LZI1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  FIFO ZERO INDEP > FZI1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  LIFO RAND INDEP > LRI1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 1049 100 1048576  FIFO RAND INDEP > FRI1049.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  FIFO ZERO NONE > FZN10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  LIFO ZERO NONE > LZN10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  LIFO RAND NONE > LRN10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  FIFO RAND NONE > FRN10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  LIFO ZERO INDEP > LZI10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  FIFO ZERO INDEP > FZI10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  LIFO RAND INDEP > LRI10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 10486 100 1048576  FIFO RAND INDEP > FRI10486.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  FIFO ZERO NONE > FZN104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  LIFO ZERO NONE > LZN104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  LIFO RAND NONE > LRN104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  FIFO RAND NONE > FRN104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  LIFO ZERO INDEP > LZI104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  FIFO ZERO INDEP > FZI104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  LIFO RAND INDEP > LRI104858.log
java -cp %CLASSPATH% org.fastfilter.xor.tester.mia.MIAAttackReplacing 6 15 104858 100 1048576  FIFO RAND INDEP > FRI104858.log
