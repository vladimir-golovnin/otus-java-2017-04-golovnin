package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectScaleQueue;

public class App
{

    public static void main( String[] args ) throws InterruptedException
    {
        ObjectScaleQueue helper = new ObjectScaleQueue();

        helper.addTask(new IntegerFactory());
        helper.addTask(new EmptyStringFactory());
        helper.addTask(new ObjectsFactory());
        helper.addTask(new ArrayFactory(0));
        helper.addTask(new ArrayFactory(1));
        helper.addTask(new ArrayFactory(2));
        helper.addTask(new ArrayListFactory(0));
        helper.addTask(new ArrayListFactory(1));
        helper.addTask(new ArrayListFactory(2));

        helper.operate();

    }
}
