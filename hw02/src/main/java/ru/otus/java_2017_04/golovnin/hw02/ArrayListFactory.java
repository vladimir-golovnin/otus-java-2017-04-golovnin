package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;

import java.util.ArrayList;

class ArrayListFactory implements ObjectFactory
{
    private final int listSize;

    public ArrayListFactory(int listSize){
        this.listSize = listSize;
    }

    @Override
    public Object getInstance()
    {
        return new ArrayList<>(listSize);
    }

    @Override
    public String getObjectName() {
        return "ArrayList of " + listSize + " elements capacity";
    }

}
