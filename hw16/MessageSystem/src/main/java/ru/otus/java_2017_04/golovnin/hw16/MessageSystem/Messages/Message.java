package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


public abstract class Message {
    public static final String TYPE_FIELD = "type";
    private final String type = this.getClass().getSimpleName();

    public String getType(){
        return type;
    }
}
