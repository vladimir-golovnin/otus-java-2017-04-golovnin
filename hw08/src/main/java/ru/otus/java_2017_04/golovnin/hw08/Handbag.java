package ru.otus.java_2017_04.golovnin.hw08;

import java.util.List;

public class Handbag {
    private int size = 3;
    private String brand = "Gucci";
    private transient double price = 2199.95;
    private byte[] coins = {1, 1, 2, 5, 10, 2, 5};
    private Crocodile crocodile;
    private List<Object> requisites;

    public Handbag(){
        crocodile = new Crocodile("Gustave", 6.5f);
        crocodile.feed(new Alarmclock(), new Fish("Salmon", 10), new Fish("Carp", 33));
    }
}
