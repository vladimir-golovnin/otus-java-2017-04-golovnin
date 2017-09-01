package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessageSystem {
    private final Map<Address, Addressee> addresses = Collections.synchronizedMap(new HashMap<>());
    private final AddressProvider addressProvider;
    private GateWay gateWay;

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

    public final void sendMessage(Command message, Address toAddress) {
        Addressee toAddressee = addresses.get(toAddress);
        if(toAddressee != null){
            toAddressee.receiveMessage(message);
        }
        else gateWay.send(message, toAddress);
    }

    public Address registerService(String serviceName, ServiceType type, Object service){
        Address address = registerAddressee(service);
        if(address != null) {
            gateWay.registerService(serviceName, type, address);
        }
        return address;
    }

    public void sendMessage(Command message, String serviceName){
        gateWay.sendToService(message, serviceName);
    }

    public void setGateWay(GateWay gateWay) {
        this.gateWay = gateWay;
    }
}
