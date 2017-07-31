package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;

class ObjectsFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new Object();
    }

    @Override
    public String getObjectName() {
        return Object.class.getSimpleName();
    }
}
