package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


public class ServiceMessage extends Message {
    private final String serviceName;
    private final String command;


    public ServiceMessage(String serviceName, String command) {
        this.serviceName = serviceName;
        this.command = command;
    }
}
