package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;

import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceType;

public class RegisterServiceMessage extends Message {

    public static final String SERVICE_NAME_FIELD_NAME = "serviceName";
    public static final String SERVICE_TYPE_FIELD_NAME = "serviceType";
    public static final String SERVICE_ADDRESS_FIELD_NAME = "address";

    private final String serviceName;
    private final ServiceType serviceType;
    private final Address address;

    public RegisterServiceMessage(String serviceName, ServiceType serviceType, Address address) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.address = address;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Address getAddress() {
        return address;
    }
}
