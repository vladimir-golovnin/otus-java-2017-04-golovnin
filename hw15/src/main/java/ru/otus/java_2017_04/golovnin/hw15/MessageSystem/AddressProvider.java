package ru.otus.java_2017_04.golovnin.hw15.MessageSystem;


import java.util.LinkedList;
import java.util.Queue;

public class AddressProvider {
    private final Queue<Address> addressPool = new LinkedList<>();

    public Address getAddress(){
        synchronized (addressPool) {
            return addressPool.poll();
        }
    }

    public void putAddress(Address address){
        synchronized (addressPool){
            addressPool.offer(address);
        }
    }

    public void putAddresses(Address... addresses){
        for (Address addr:addresses
             ) {
            putAddress(addr);
        }
    }
}
