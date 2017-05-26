package ru.otus.java_2017_04.golovnin.hw07;

import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ATMtest {
    private static final Currency RUBLE = Currency.getInstance("RUB");

    private ATM createATMforTest(){
        List<BanknoteCell> cells = new LinkedList<>();
        cells.add(new BanknoteCell(RUBLE, 5000, 250));
        cells.add(new BanknoteCell(RUBLE, 100, 250));
        cells.add(new BanknoteCell(RUBLE, 500, 100));
        cells.add(new BanknoteCell(RUBLE, 1000, 100));
        cells.add(new BanknoteCell(RUBLE, 50, 50));
        ATM atm = new ATM(RUBLE, cells);
        return atm;
    }

    @Test
    public void checkEmptyATM(){
        ATM atm = createATMforTest();
        Assert.assertEquals(0, atm.getBallance());
        List<Banknote> cash = atm.withdraw(900);
        Assert.assertTrue(cash.isEmpty());
    }

    @Test
    public void test50Rub(){
        ATM atm = createATMforTest();
        List<Banknote> cash = new LinkedList<>();
        cash.add(new Banknote(RUBLE,50));
        cash = atm.putCash(cash);
        Assert.assertEquals(50, atm.getBallance());
        Assert.assertTrue(cash.isEmpty());
        cash = atm.withdraw(50);
        Assert.assertTrue(cash.size() == 1);
        Assert.assertEquals(50, cash.get(0).getNominal());
        Assert.assertSame(RUBLE, cash.get(0).getCurrency());
    }

    @Test
    public void test100Rub(){
        ATM atm = createATMforTest();
        List<Banknote> cash = new LinkedList<>();
        cash.add(new Banknote(RUBLE,100));
        cash = atm.putCash(cash);
        Assert.assertEquals(100, atm.getBallance());
        Assert.assertTrue(cash.isEmpty());
        cash = atm.withdraw(100);
        Assert.assertTrue(cash.size() == 1);
        Assert.assertEquals(100, cash.get(0).getNominal());
        Assert.assertSame(RUBLE, cash.get(0).getCurrency());
    }

    @Test
    public void test2banknotes(){
        ATM atm = createATMforTest();
        List<Banknote> cash = new LinkedList<>();
        cash.add(new Banknote(RUBLE,50));
        cash.add(new Banknote(RUBLE, 50));
        cash = atm.putCash(cash);
        Assert.assertEquals(100, atm.getBallance());
        Assert.assertTrue(cash.isEmpty());
        cash = atm.withdraw(100);
        Assert.assertTrue(cash.size() == 2);
        Assert.assertEquals(50, cash.get(0).getNominal());
        Assert.assertSame(RUBLE, cash.get(0).getCurrency());
        Assert.assertEquals(50, cash.get(1).getNominal());
        Assert.assertSame(RUBLE, cash.get(1).getCurrency());
    }

    @Test
    public void testMinAmount(){
        ATM atm = createATMforTest();
        List<Banknote> cash = new LinkedList<>();
        cash.add(new Banknote(RUBLE,50));
        cash.add(new Banknote(RUBLE, 50));
        cash.add(new Banknote(RUBLE, 100));
        cash = atm.putCash(cash);
        Assert.assertEquals(200, atm.getBallance());
        Assert.assertTrue(cash.isEmpty());
        cash = atm.withdraw(100);
        Assert.assertTrue(cash.size() == 1);
        Assert.assertEquals(100, cash.get(0).getNominal());
        Assert.assertSame(RUBLE, cash.get(0).getCurrency());
    }

    @Test
    public void testInvalidBanknote(){
        ATM atm = createATMforTest();
        List<Banknote> cash = new LinkedList<>();
        cash.add(new Banknote(RUBLE,111));
        cash = atm.putCash(cash);
        Assert.assertEquals(0, atm.getBallance());
        Assert.assertTrue(cash.size() == 1);
        Assert.assertEquals(111, cash.get(0).getNominal());
        Assert.assertSame(RUBLE, cash.get(0).getCurrency());
        cash = atm.withdraw(100);
        Assert.assertTrue(cash.isEmpty());
    }

}
