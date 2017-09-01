package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private Map<String, ServiceRecord> serviceMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Address, String> addressToServiceMap = Collections.synchronizedMap(new HashMap<>());

    public void register(String serviceName, ServiceType type, Address address){
        if(serviceName != null && address != null) {
            if (!serviceMap.containsKey(serviceName)) {
                ServiceRecord record = new ServiceRecord(type);
                record.addAddress(address);
                serviceMap.put(serviceName, record);
                addressToServiceMap.put(address, serviceName);
            } else {
                serviceMap.get(serviceName).addAddress(address);
                addressToServiceMap.put(address, serviceName);
            }
        }
    }


    public void deregister(Address address){
        if(address != null){
            String serviceName = addressToServiceMap.remove(address);
            ServiceRecord record = serviceMap.get(serviceName);
            record.removeAddress(address);
            if(record.getAddressCount() == 0) serviceMap.remove(serviceName);
        }
    }

    public ServiceRecord getRecord(String serviceName){
        return serviceMap.get(serviceName);
    }
}
