package ru.otus.java_2017_04.golovnin.hw14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelSorter implements IntegerArraySorter{
    private final ExecutorService execService;
    private final AtomicInteger sortersCounter = new AtomicInteger(0);

    public ParallelSorter(int threadsNumber){
        execService = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void sort(int[] array) {
        createSpawnSorter(array, 0, array.length - 1);
        while (!taskIsDone());
    }

    private void createSpawnSorter(int[] array, int start, int end){
        execService.submit(new SpawnSorter(array, start, end));
        sortersCounter.incrementAndGet();
    }

    private void removeSpawnSorter(){
        sortersCounter.decrementAndGet();
    }

    private boolean taskIsDone(){
        return sortersCounter.get() == 0;
    }

    private class SpawnSorter implements Runnable{

        private final int[] array;
        private final int startIndex;
        private final int endIndex;

        public SpawnSorter(int[] array, int start, int end){
            this.array = array;
            this.startIndex  = start;
            this.endIndex = end;
        }

        @Override
        public void run() {
            int index = partition(array, startIndex, endIndex);
            if (startIndex < index - 1) {
                createSpawnSorter(array, startIndex, index - 1);
            }
            if (index < endIndex) {
                createSpawnSorter(array, index, endIndex);
            }
            removeSpawnSorter();
        }

        private int partition(int arr[], int left, int right)
        {
            int i = left, j = right;
            int tmp;
            int pivot = arr[(left + right) / 2];

            while (i <= j) {
                while (arr[i] < pivot)
                    i++;
                while (arr[j] > pivot)
                    j--;
                if (i <= j) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    i++;
                    j--;
                }
            }

            return i;
        }
    }
}
