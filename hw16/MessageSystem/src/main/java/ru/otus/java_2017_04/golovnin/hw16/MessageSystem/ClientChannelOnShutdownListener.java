package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


public interface ClientChannelOnShutdownListener {
    void inform(ClientChannel channel);
}
