package ru.otus.java_2017_04.golovnin.hw08;


public class ObjectConverter extends ToJsonConverter{
    private ToJsonConverter elementsConverter;

    public ObjectConverter(ToJsonConverter elementsConverter){
        this.elementsConverter = elementsConverter;
    }

    @Override
    public String convert(Object obj) {
        return super.convert(obj);
    }
}
