package ru.otus.java_2017_04.golovnin.hw14;


import java.util.Arrays;

public class LibrarySorter implements IntegerArraySorter{
    @Override
    public void sort(int[] array) {
        Arrays.sort(array);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
