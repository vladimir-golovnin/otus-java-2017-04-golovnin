package ru.otus.java_2017_04.golovnin.hw14;


import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractSortersTestRig {
    private final Queue<IntegerArraySorter> sorters = new LinkedList<>();

    public final void registerSorter(IntegerArraySorter sorter){
        sorters.offer(sorter);
    }

    public final void start(){
        IntegerArraySorter sorter;
        while ((sorter = sorters.poll()) != null){
            test(sorter);
        }
    }

    protected abstract void test(IntegerArraySorter sorter);
}
