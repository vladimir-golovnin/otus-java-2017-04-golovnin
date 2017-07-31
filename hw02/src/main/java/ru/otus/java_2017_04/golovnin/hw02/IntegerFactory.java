package ru.otus.java_2017_04.golovnin.hw02;

import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;

class IntegerFactory implements ObjectFactory
{
    private static final int VALUE = 1;

    @Override
    public Object getInstance() {
        return new Integer(VALUE);
    }

    @Override
    public String getObjectName() {
        return Integer.class.getSimpleName();
    }
}
