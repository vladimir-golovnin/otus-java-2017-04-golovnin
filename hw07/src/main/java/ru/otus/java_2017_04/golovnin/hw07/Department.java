package ru.otus.java_2017_04.golovnin.hw07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Department implements DepartmentInterface{
    List<ATMInterface> atms = new LinkedList<>();
    Map<ATMInterface, ATMMemento> atmMementosMap = new HashMap<>();

    @Override
    public void addATM(ATMInterface atm){
        if(atm != null) {
            atms.add(atm);
            atmMementosMap.put(atm, atm.getMemento());
        }
    }

    @Override
    public int getATMsBalance() {
        int  balance = 0;
        for (ATMInterface atm:atms
             ) {
            balance += atm.getBalance();
        }
        return balance;
    }

    @Override
    public void ATMsRestore() {
        for (ATMInterface atm:atms
             ) {
            atm.restore(atmMementosMap.get(atm));
        }
    }

}
