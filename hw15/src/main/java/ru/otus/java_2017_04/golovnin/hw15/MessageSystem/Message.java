package ru.otus.java_2017_04.golovnin.hw15.MessageSystem;


import java.util.function.Consumer;

public abstract class Message {
    private final Address fromAddress;

    public Message(Address from){
        fromAddress = from;
    }

    public Address getFromAddress() {
        return fromAddress;
    }

    public abstract Consumer<Addressee> execute(Object obj);
}
