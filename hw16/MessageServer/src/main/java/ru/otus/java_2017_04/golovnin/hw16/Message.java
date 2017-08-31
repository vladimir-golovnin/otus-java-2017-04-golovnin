package ru.otus.java_2017_04.golovnin.hw16;


public abstract class Message {
    public static final String TYPE_FIELD = "type";
    private final String type;

    public Message(String type){
        this.type = type;
    }
}
