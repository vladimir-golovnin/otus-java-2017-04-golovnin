package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;

public class DirectMessage extends Message {
    private Address address;
    private String command;


    public DirectMessage(Address address, String command) {
        this.address = address;
        this.command = command;
    }
}
