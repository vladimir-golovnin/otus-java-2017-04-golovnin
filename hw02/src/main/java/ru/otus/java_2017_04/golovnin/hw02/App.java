package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectScaleQueue;

import java.util.ArrayList;

public class App
{

    public static void main( String[] args ) throws InterruptedException
    {
        ObjectScaleQueue helper = new ObjectScaleQueue();

        helper.addTask(() -> new Integer(1), Integer.class.getSimpleName());
        helper.addTask(() -> new String(""), "empty String");
        helper.addTask(() -> new Object(), Object.class.getSimpleName());
        helper.addTask(() -> new Object[0], "Array of 0 Objects");
        helper.addTask(() -> new Object[1], "Array of 1 Objects");
        helper.addTask(() -> new Object[2], "Array of 2 Objects");
        helper.addTask(() -> new ArrayList<>(0), "ArrayList of 0 elements capacity");
        helper.addTask(() -> new ArrayList<>(1), "ArrayList of 1 elements capacity");
        helper.addTask(() -> new ArrayList<>(2), "ArrayList of 2 elements capacity");

        helper.operate();

    }
}
