package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


public class DirectMessage extends Message{
    private Address address;
    private Command command;


    public DirectMessage(Address address, Command command) {
        this.address = address;
        this.command = command;
    }
}
