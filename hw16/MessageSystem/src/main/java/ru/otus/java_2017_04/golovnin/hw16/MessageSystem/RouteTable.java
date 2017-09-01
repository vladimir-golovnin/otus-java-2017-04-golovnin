package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;

public interface RouteTable {
    void addChannel(ClientChannel channel);

    Address getAddress(ClientChannel channel);

    ClientChannel route(Address address);

    void removeChannel(ClientChannel channel);
}
