package ru.otus.java_2017_04.golovnin.hw14;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SorterTests
{
    private static final int[] UNSORTED_ARRAY = getTestArray();
    private static final int[] SORTED_ARRAY = getSortedArray();

    private static int[] getTestArray(){
        final int TEST_ARRAY_LENGTH = 5_000_000;
        final int SEED = 0;
        final int BOUND = 1000;
        final int[] testArray = new int[TEST_ARRAY_LENGTH];
        Random random = new Random(SEED);
        for(int index = 0; index < testArray.length; index++){
            testArray[index] = random.nextInt(BOUND);
        }
        return testArray;
    }

    private static int[] getSortedArray(){
        int[] result = Arrays.copyOf(UNSORTED_ARRAY, UNSORTED_ARRAY.length);
        Arrays.sort(result);
        return result;
    }

    @Test
    public void sortTest(){
        IntegerArraySorter sorter = new ParallelSorter(1);
        //IntegerArraySorter sorter = new ReferenceIntArraySorter();
        //System.out.println(Arrays.toString(UNSORTED_ARRAY));
        sorter.sort(UNSORTED_ARRAY);

        Assert.assertArrayEquals(SORTED_ARRAY, UNSORTED_ARRAY);
        //System.out.println(Arrays.toString(UNSORTED_ARRAY));
    }


}
