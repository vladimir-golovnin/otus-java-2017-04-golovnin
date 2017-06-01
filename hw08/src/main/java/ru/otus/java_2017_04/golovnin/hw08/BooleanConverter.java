package ru.otus.java_2017_04.golovnin.hw08;


public class BooleanConverter extends ToJsonConverter{
    @Override
    public String convert(Object obj) {
        if(isBoolean(obj)) return obj.toString();
        else return super.convert(obj);
    }

    private boolean isBoolean(Object obj){
        return obj.getClass() == Boolean.class;
    }
}
