package ru.otus.java_2017_04.golovnin.hw07;


import java.util.Map;

public class ATMMemento {
    private Map<BanknoteCell, BanknoteCellMemento> mementos;

    public ATMMemento(Map<BanknoteCell, BanknoteCellMemento> cellMementos){
        mementos = cellMementos;
    }

    public Map<BanknoteCell, BanknoteCellMemento> getMementos(){
        return mementos;
    }
}
