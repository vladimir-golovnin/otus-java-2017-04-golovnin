package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.util.LinkedList;
import java.util.List;

public class AddressesProvideMessage extends Message{
    public static final String ADDRESSES_FIELD_NAME = "addresses";

    public List<Address> getAddresses() {
        return addresses;
    }

    private final List<Address> addresses = new LinkedList<>();

    public void addAddress(Address address){
        addresses.add(address);
    }
}
