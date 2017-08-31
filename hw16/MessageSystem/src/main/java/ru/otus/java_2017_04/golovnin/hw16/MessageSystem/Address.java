package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


public class Address {
    private int id;

    public Address(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj != null){
            Address someAddress = (Address)obj;
            result = id == someAddress.id;
        }

        return result;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
