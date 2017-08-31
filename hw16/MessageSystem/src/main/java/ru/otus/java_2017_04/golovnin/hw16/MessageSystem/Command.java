package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.util.function.Consumer;

public abstract class Command {
    private final Address fromAddress;

    public Command(Address from){
        fromAddress = from;
    }

    public Address getFromAddress() {
        return fromAddress;
    }

    public abstract Consumer<Addressee> execute(Object obj);
}
