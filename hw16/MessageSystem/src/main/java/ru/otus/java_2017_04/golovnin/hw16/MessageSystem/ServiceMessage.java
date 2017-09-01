package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


public class ServiceMessage extends Message{
    private final String serviceName;
    private final Command command;


    public ServiceMessage(String serviceName, Command command) {
        this.serviceName = serviceName;
        this.command = command;
    }
}
