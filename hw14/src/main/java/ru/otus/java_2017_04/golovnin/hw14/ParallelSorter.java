package ru.otus.java_2017_04.golovnin.hw14;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSorter implements IntegerArraySorter{
    private final ExecutorService execService;
    private final Queue<SpawnSorter> sortersPool;
    private final int threadsNumber;

    public ParallelSorter(int threadsNumber){
        this.threadsNumber = threadsNumber;
        execService = Executors.newFixedThreadPool(threadsNumber);
        sortersPool = new ConcurrentLinkedQueue<>();
        while (sortersPool.size() < threadsNumber){
            sortersPool.offer(new SpawnSorter());
        }
    }

    @Override
    public void sort(int[] array) {
        sortersPool.poll().executeTask(array, 0, array.length - 1);
        while (!taskIsDone());
        execService.shutdown();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName() + " with " + threadsNumber + " threads";
    }


    private boolean taskIsDone(){
        return threadsNumber == sortersPool.size();
    }

    private class SpawnSorter implements Runnable{

        private int[] array;
        private int startIndex;
        private int endIndex;


        public void executeTask(int[] array, int start, int end){
            this.array = array;
            this.startIndex = start;
            this.endIndex = end;
            execService.submit(this);
        }

        @Override
        public void run() {
            quickSort(startIndex, endIndex);
            sortersPool.offer(this);
        }

        private void quickSort(int left, int right) {
            int index = partition(array, left, right);
            if (left < index - 1) {
                selectExecutor(left, index - 1);
            }
            if (index < right) {
                selectExecutor(index, right);
            }
        }

        private void selectExecutor(int left, int right){
            SpawnSorter subsorter = sortersPool.poll();
            if(subsorter != null) subsorter.executeTask(array, left, right);
            else quickSort(left, right);
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
