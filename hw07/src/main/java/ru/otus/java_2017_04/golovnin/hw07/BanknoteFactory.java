package ru.otus.java_2017_04.golovnin.hw07;


import java.util.*;

public class BanknoteFactory {
    private Currency currency;
    private Set<Integer> nominalSet;

    public BanknoteFactory(Currency currency, int[] nominalSet) {
        this.currency = currency;
        this.nominalSet = new HashSet<>();
        for(int nominal : nominalSet){
            this.nominalSet.add(nominal);
        }
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public Iterator<Integer> nominalsIterator(){
        return nominalSet.iterator();
    }

    public Banknote getBanknote(int nominal){
        if(nominalSet.contains(nominal)) return new Banknote(currency, nominal);
        else throw new NoSuchElementException();
    }

    public List<Banknote> getBanknotes(int nominal, int amount){
        List<Banknote> banknotes = new LinkedList<>();
        if (nominalSet.contains(nominal)) {
            for (int counter = 0; counter < amount; counter++){
                banknotes.add(new Banknote(this.currency, nominal));
            }
        }
        return banknotes;
    }
}