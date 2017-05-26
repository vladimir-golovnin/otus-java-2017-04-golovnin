package ru.otus.java_2017_04.golovnin.hw07;


import java.util.EventListener;
import java.util.List;

public interface ATMInterface extends EventListener{
    int getBalance();
    ATMMemento getMemento();
    void restore(ATMMemento memento);
    List<Banknote> withdraw(int sum);
}
