package ru.otus.java_2017_04.golovnin.hw08;


public class Jameson {

    private ToJsonConverter converterChain;

    public Jameson(){
        converterChain = new BooleanConverter();
        converterChain.chain(new NumberConverter());
        converterChain.chain(new CharecterConverter());
        converterChain.chain(new StringConverter());
        converterChain.chain(new ArrayConverter(converterChain));
        converterChain.chain(new ObjectConverter(converterChain));
    }

    public String toJson(Object obj){
        return converterChain.convert(obj);
    }

}
