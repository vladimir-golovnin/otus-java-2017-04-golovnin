package ru.otus.java_2017_04.golovnin.hw16;


public class AllocateAddressesMessage extends Message {
    public static final String TYPE = "AllocateAddresses";
    private final int addressesNum;
    public AllocateAddressesMessage(int num) {
        super(TYPE);
        addressesNum = num;
    }
}
