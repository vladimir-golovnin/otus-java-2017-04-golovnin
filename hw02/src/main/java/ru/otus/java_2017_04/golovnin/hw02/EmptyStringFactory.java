package ru.otus.java_2017_04.golovnin.hw02;


import ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting.ObjectFactory;

class  EmptyStringFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new String("");
    }

    @Override
    public String getObjectName() {
        return "empty String";
    }
}
