package ru.otus.java_2017_04.golovnin.hw07;

import java.util.Currency;

public class Banknote{
    private int nominal;
    private Currency currency;
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("RUB");

    public int getNominal(){
        return nominal;
    }

    public Currency getCurrency(){
        return currency;
    }

    public Banknote(Currency currency, int nominal){
        if(currency != null) this.currency = currency;
        else this.currency = DEFAULT_CURRENCY;
        this.nominal = nominal;
    }

    public boolean equals(Banknote comparedBanknote){
        return comparedBanknote.getCurrency() == this.currency && comparedBanknote.getNominal() == this.nominal;
    }
}
