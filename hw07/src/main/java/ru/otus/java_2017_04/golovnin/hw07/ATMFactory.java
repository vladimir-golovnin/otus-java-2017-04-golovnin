package ru.otus.java_2017_04.golovnin.hw07;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ATMFactory {
    public enum ATM_TYPE {TYPE1, TYPE2};

    private static final int TYPE1_CAPACITY = 95;
    private static final float LOAD_FACTOR1 = 0.75f;
    private static final int TYPE2_CAPACITY = 130;
    private static final float LOAD_FACTOR2 = 0.5f;

    public static ATMInterface createATM(BanknoteFactory banknoteFactory, ATM_TYPE type){
        int capacity;
        float loadFactor;

        switch (type){
            case TYPE1:
                capacity = TYPE1_CAPACITY;
                loadFactor = LOAD_FACTOR1;
                break;
            case TYPE2:
                capacity = TYPE2_CAPACITY;
                loadFactor = LOAD_FACTOR2;
                break;
            default:
                capacity = TYPE1_CAPACITY;
                loadFactor = LOAD_FACTOR1;
        }
        int load = (int)(capacity * loadFactor);
        Iterator<Integer> nominalIterator = banknoteFactory.nominalsIterator();

        List<BanknoteCell> cells = new LinkedList<>();
        while (nominalIterator.hasNext()){
            cells.add(new BanknoteCell(banknoteFactory.getCurrency(), nominalIterator.next(), capacity));
        }

        ATM atm = new ATM(banknoteFactory.getCurrency(), cells);

        nominalIterator = banknoteFactory.nominalsIterator();
        while (nominalIterator.hasNext()){
            atm.putCash(banknoteFactory.getBanknotes(nominalIterator.next(), load));
        }
        return atm;
    }
}
