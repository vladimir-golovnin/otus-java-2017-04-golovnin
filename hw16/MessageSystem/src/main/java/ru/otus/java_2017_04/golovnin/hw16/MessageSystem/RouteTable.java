package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;

public interface RouteTable {
    byte MAX_WORKLOAD = 100;

    void addChannel(ClientChannel channel);

    Address getAddress(ClientChannel channel);

    ClientChannel route(Address address);

    void removeChannel(ClientChannel channel);

    byte getWorkloadForAddress(Address address);

    void submitWorkload(Address address, byte workload);
}
