package ru.otus.java_2017_04.golovnin.hw14;


public class CustomQuickSorter implements IntegerArraySorter{
    public void sort(int[] array){
        quickSort(array, 0, array.length - 1);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    int partition(int arr[], int left, int right)
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

    void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }
}
