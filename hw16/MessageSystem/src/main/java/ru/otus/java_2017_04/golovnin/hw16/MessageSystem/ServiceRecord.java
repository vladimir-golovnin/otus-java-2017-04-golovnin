package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServiceRecord{
    private final Set<Address> addresses = Collections.synchronizedSet(new HashSet<>());
    private final ServiceType type;

    public ServiceRecord(ServiceType type) {
        this.type = type;
    }

    public void addAddress(Address address){
        this.addresses.add(address);
    }

    public void removeAddress(Address address){
        addresses.remove(address);
    }

    public int  getAddressCount(){
        return addresses.size();
    }

    public Set<Address> addresses(){
        return addresses;
    }

    public ServiceType type(){
        return type;
    }
}
