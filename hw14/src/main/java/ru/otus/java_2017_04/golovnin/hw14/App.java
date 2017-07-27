package ru.otus.java_2017_04.golovnin.hw14;


public class App {
    public static void main(String[] args){
        AbstractSortersTestRig testRig = new SortersPerformanceTestRig();
        testRig.registerSorter(new LibrarySorter());
        testRig.registerSorter(new CustomQuickSorter());
        testRig.registerSorter(new ParallelSorter(1));
        testRig.registerSorter(new ParallelSorter(2));
        testRig.registerSorter(new ParallelSorter(4));
        testRig.start();
    }
}
