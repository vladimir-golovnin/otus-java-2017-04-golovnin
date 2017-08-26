package ru.otus.java_2017_04.golovnin.hw15.MessageSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessageSystem {
    private final Map<Address, Addressee> addresses = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Address> serviceAddresses = Collections.synchronizedMap(new HashMap<>());
    private final AddressProvider addressProvider;

    public MessageSystem(AddressProvider addressProvider){
        this.addressProvider = addressProvider;
    }

    public final Address registerAddressee(Object object) {
        Address address = addressProvider.getAddress();
        if(address != null) {
            Addressee addressee = new Addressee(this, address, object);
            addresses.put(address, addressee);
            addressee.live();
        }
        return address;
    }

    public final void deregister(Address address) {
        if(address != null) {
            Addressee addressee = addresses.get(address);
            if(addressee != null){
                addresses.remove(address);
                addressProvider.putAddress(address);
            }
            addressee.die();
        }
    }

    public final void sendMessage(Message message, Address toAddress) {
        Addressee toAddressee = addresses.get(toAddress);
        if(toAddressee != null){
            toAddressee.receiveMessage(message);
        }
    }

    public Address registerService(String serviceName, Object service){
        Address address = registerAddressee(service);
        serviceAddresses.put(serviceName, address);
        return address;
    }

    public void sendMessage(Message message, String serviceName){
        Address toAddress;
        toAddress = serviceAddresses.get(serviceName);
        sendMessage(message, toAddress);
    }
}
