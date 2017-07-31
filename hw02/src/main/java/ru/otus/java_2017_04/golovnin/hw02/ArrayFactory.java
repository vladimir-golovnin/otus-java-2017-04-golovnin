package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;

class ArrayFactory implements ObjectFactory
{
    private final int arraySize;

    public ArrayFactory(int arraySize){
        this.arraySize = arraySize;
    }

    @Override
    public Object getInstance() {
        return new Object[arraySize];
    }

    @Override
    public String getObjectName() {
        return "Array of " + arraySize + " Objects";
    }
}
