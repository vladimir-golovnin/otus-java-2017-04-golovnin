package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages;


public class AllocateAddressesMessage extends Message {
    public static final String ADDRESSES_NUM_FIELD_NAME = "addressesNum";
    private final int addressesNum;

    public AllocateAddressesMessage(int num) {
        addressesNum = num;
    }
}
