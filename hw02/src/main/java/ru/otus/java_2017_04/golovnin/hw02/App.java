package ru.otus.java_2017_04.golovnin.hw02;

import java.util.ArrayList;

public class App
{

    public static void main( String[] args ) throws InterruptedException
    {
        ObjectScaleHelper helper = new ObjectScaleHelper();

        helper.addTask(new IntegerFactory());
        helper.addTask(new EmptyStringFactory());
        helper.addTask(new StringFactory());
        helper.addTask(new ObjectsFactory());
        helper.addTask(new ArrayListFactory());
        helper.addTask(new ArrayFactory());
        helper.operate();

    }


}

class IntegerFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new Integer(1);
    }
}

class  EmptyStringFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new String("");
    }
}

class  StringFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new String("s");
    }
}

class ObjectsFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new Object();
    }
}

class ArrayListFactory implements ObjectFactory
{
    @Override
    public Object getInstance()
    {
        return new ArrayList<Object>(10);
    }

}

class ArrayFactory implements ObjectFactory
{

    @Override
    public Object getInstance() {
        return new Object[4];
    }
}
