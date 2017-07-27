package ru.otus.java_2017_04.golovnin.hw14;

import java.util.Arrays;
import java.util.Random;

public class SortersPerformanceTestRig extends AbstractSortersTestRig{

    protected void test(IntegerArraySorter sorter) {
        int[] testArray = getTestArray();
        int[] sortedArray = getSortedArray(testArray);

        long startTime = System.currentTimeMillis();
        sorter.sort(testArray);
        long workDuration = System.currentTimeMillis() - startTime;
        boolean workResultOk = Arrays.equals(testArray, sortedArray);

        System.out.println(sorter.getName());
        System.out.println("Work completed in " + workDuration + " ms");
        System.out.println("Work result is " + (workResultOk ? "OK" : "NOT OK"));
        System.out.println();
        System.gc();
    }

    private int[] getSortedArray(int[] testArray) {
        int[] result = Arrays.copyOf(testArray, testArray.length);
        Arrays.sort(result);
        return result;
    }

    private int[] getTestArray() {
        final int TEST_ARRAY_LENGTH = 20_000_000;
        final int SEED = 0;
        final int BOUND = 10_000;
        final int[] testArray = new int[TEST_ARRAY_LENGTH];
        Random random = new Random(SEED);
        for(int index = 0; index < testArray.length; index++){
            testArray[index] = random.nextInt(BOUND);
        }
        return testArray;
    }
}
