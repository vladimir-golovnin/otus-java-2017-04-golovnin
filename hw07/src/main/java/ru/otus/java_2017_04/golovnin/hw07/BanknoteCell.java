package ru.otus.java_2017_04.golovnin.hw07;

import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BanknoteCell {
    private int nominal;
    private Currency currency;
    private int count = 0;
    private int capacity;
    private Stack<Banknote> roll = new Stack<>();
    private BanknoteCell nextCell = null;


    public BanknoteCell(Currency currency, int nominal, int capacity){
        this.currency = currency;
        this.nominal = nominal;
        this.capacity = capacity;
    }

    public int getNominal(){
        return nominal;
    }

    public Currency getCurrency(){
        return currency;
    }

    public int count(){
        return count;
    }

    public BanknoteCell link(BanknoteCell cell){
        BanknoteCell result = this;
        if(cell != null && cell != this){
            if(cell.getNominal() > this.getNominal()){
                cell.link(this);
                result = cell;
            }
            else {
                if(nextCell == null) nextCell = cell;
                else nextCell = nextCell.link(cell);
            }
        }
        return result;
    }

    public Banknote put(Banknote banknote){
        Banknote result = null;
        if(banknote.getCurrency().equals(currency)
                && banknote.getNominal() == nominal
                && count < capacity) {
            roll.push(banknote);
            count++;
            result = null;
        }
        else {
            if(nextCell != null){
                result = nextCell.put(banknote);
            }
            else result = banknote;
        }
        return result;
    }


    public List<Banknote> withdraw(int requestedSum){
        List<Banknote> result = new LinkedList<>();
        if(requestedSum > 0) {
            int expectedCount = requestedSum / nominal;
            int ensuredCount = Math.min(expectedCount, count);
            int ensuredSum = ensuredCount * nominal;
            if (ensuredSum == requestedSum) {
                result.addAll(withdrawAmount(ensuredCount));
            } else if (nextCell != null) {
                result.addAll(nextCell.withdraw(requestedSum - ensuredSum));
                if (!result.isEmpty()) {
                    result.addAll(withdrawAmount(ensuredCount));
                }
            }
        }
        return result;
    }

    public List<Banknote> withdrawAmount(int amount){
        List<Banknote> result = new LinkedList<>();
        if(amount <= count){
            for(int counter = 0; counter < amount; counter++){
                result.add(roll.pop());
                count--;
            }
        }
        return result;
    }
}
