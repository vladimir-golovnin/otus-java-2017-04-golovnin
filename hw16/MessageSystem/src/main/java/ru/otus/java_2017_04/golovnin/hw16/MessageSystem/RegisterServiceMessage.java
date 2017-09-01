package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;

public class RegisterServiceMessage extends Message {

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
