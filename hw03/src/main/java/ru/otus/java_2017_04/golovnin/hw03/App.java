package ru.otus.java_2017_04.golovnin.hw03;

import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        show("Build initial MyArrayList.");
        List<Integer> myArrayList = new MyArrayList<>(0);
        final int NUMBER_OF_ELEMENTS = 17;
        for(int i = 0; i < NUMBER_OF_ELEMENTS; i++){
            myArrayList.add(i);
        }
        show("MyArrayList contents: " + myArrayList);

        Integer[] elementsToAdd = {100, 101, 102, 103, 104, 105};
        show("\nAdding elements " + Arrays.asList(elementsToAdd ) + " to MyArrayList");
        Collections.addAll(myArrayList, elementsToAdd);
        show("MyArrayList contents: " + myArrayList);

        Integer[] copiedArray = new Integer[6];
        Arrays.setAll(copiedArray, i -> i + 1000);
        List<Integer> copiedList = Arrays.asList(copiedArray);
        show("\nCopying " + copiedList + " to MyArrayList");
        Collections.copy(myArrayList, copiedList);
        show("MyArrayList contents: " + myArrayList);

        Integer[] destinationArray = new Integer[30];
        Arrays.setAll(destinationArray, i -> i + 2000);
        List<Integer> destinationList = Arrays.asList(destinationArray);
        show("\nDestinationList contents"+ destinationList);
        show("Copying data from MyArrayList to DestinationList.");
        Collections.copy(destinationList, myArrayList);
        show("DestinationList contents: " + destinationList);

        show("\nMoving Data from DestinationList to MyArrayList");
        myArrayList.clear();
        Collections.addAll(myArrayList, destinationList.toArray(new Integer[0]));
        show("MyArrayList contents: " + myArrayList);

        Collections.sort(myArrayList, (a, b) -> a - b );
        show("Sorted by ascending MyArrayList contents: " + myArrayList);

        Collections.sort(myArrayList, (a, b) -> b - a);
        show("Sorted by descending MyArrayList contents: " + myArrayList);
    }

    private static void show(String msg){
        System.out.println(msg);
    }


}
