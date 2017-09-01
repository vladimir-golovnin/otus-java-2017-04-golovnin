package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;

public class DirectMessage extends Message {
    public static final String ADDRESS_FIELD_NAME = "address";
    public static final String COMMAND_FIELD_NAME = "command";
    public static final String COMMAND_NAME_FIELD_NAME = "commandName";

    private Address address;
    private String commandName;
    private String command;


    public DirectMessage(Address address, String commandName, String command) {
        this.address = address;
        this.commandName = commandName;
        this.command = command;
    }
}
