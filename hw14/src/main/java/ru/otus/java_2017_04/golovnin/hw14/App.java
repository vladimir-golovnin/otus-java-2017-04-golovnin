package ru.otus.java_2017_04.golovnin.hw14;


public class App {
    public static void main(String[] args){
        AbstractSortersTestRig testRig = new SortersPerformanceTestRig();
       // testRig.registerSorter(new LibrarySorter());
        //testRig.registerSorter(new CustomQuickSorter());

        for(int i = 0; i < 50; i++) {
            testRig.registerSorter(new ParallelSorter(10));
        }

        testRig.start();
    }
}
