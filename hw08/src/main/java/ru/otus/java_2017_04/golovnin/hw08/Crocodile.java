package ru.otus.java_2017_04.golovnin.hw08;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Crocodile {
    private String name;
    private float length;
    private double bellyfulCoef = 0;
    private Object[] lunch;

    public Crocodile(String name, float length)
    {
        this.name = name;
        this.length = length;
    }

    public void feed(Object... foods){
        lunch = foods;
    }
}
