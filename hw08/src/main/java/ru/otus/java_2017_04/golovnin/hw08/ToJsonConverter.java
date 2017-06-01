package ru.otus.java_2017_04.golovnin.hw08;


public abstract class ToJsonConverter {
    private ToJsonConverter nextConverter = null;

    public final void chain (ToJsonConverter converter){
        if(converter != null){
            if(nextConverter == null) nextConverter = converter;
            else nextConverter.chain(converter);
        }
    }

    public String convert(Object obj){
        String result = "";
        if(nextConverter != null) result = nextConverter.convert(obj);
        return result;
    }
}
