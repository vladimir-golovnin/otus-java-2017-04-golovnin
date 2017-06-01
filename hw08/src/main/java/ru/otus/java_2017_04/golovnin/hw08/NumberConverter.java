package ru.otus.java_2017_04.golovnin.hw08;


public class NumberConverter extends ToJsonConverter {

    @Override
    public String convert(Object obj) {
        if(isNumber(obj)) return obj.toString();
        else return super.convert(obj);
    }

    private boolean isNumber(Object obj){
        return obj.getClass().getSuperclass() == Number.class;
    }
}
