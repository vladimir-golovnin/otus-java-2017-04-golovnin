package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


public class ServiceMessage extends Message {
    public static final String TO_SERVICE_FIELD_NAME = "serviceName";
    private final String serviceName;
    private final String command;
    private String commandName;

    public ServiceMessage(String serviceName, String commandName, String command) {
        this.serviceName = serviceName;
        this.command = command;
        this.commandName = commandName;
    }
}
