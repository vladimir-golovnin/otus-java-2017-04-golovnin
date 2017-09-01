package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RouteTableImpl implements RouteTable {
    private final Map<Address, ClientChannel> routeMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<ClientChannel, List<Address>> clientAddressesMap = Collections.synchronizedMap(new HashMap<>());
    private final AddressProvider addressProvider;

    public RouteTableImpl(AddressProvider addressProvider){
        this.addressProvider = addressProvider;
    }

    @Override
    public void addChannel(ClientChannel channel){
        if(channel != null && !clientAddressesMap.containsKey(channel)) {
            clientAddressesMap.put(channel, new LinkedList<>());
        }
    }

    @Override
    public void removeChannel(ClientChannel channel){
        if(channel != null){
            if(clientAddressesMap.containsKey(channel)){
                List<Address> addresses = clientAddressesMap.get(channel);
                addresses.forEach(address -> {
                    routeMap.remove(address);
                    addressProvider.putAddress(address);
                });
                clientAddressesMap.remove(channel);
            }
        }
    }

    @Override
    public Address getAddress(ClientChannel channel){
        Address address = null;
        if(clientAddressesMap.containsKey(channel)) {
            address = addressProvider.getAddress();
            clientAddressesMap.get(channel).add(address);
            routeMap.put(address, channel);
        }
        return address;
    }

    @Override
    public ClientChannel route(Address address){
        return routeMap.get(address);
    }

}
