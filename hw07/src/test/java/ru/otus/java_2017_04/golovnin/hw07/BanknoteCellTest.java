package ru.otus.java_2017_04.golovnin.hw07;

import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;
import java.util.List;

public class BanknoteCellTest {
    private static final Currency TEST_CURRENCY = Currency.getInstance("RUB");

    @Test
    public void restoreTest(){
        int TEST_NOMINAL = 100;

        BanknoteCell cell = new BanknoteCell(TEST_CURRENCY, TEST_NOMINAL, 50);
        int initialAmount = 33;
        for(int counter = initialAmount; counter > 0; counter--){
            cell.put(new Banknote(TEST_CURRENCY, TEST_NOMINAL));
        }
        Assert.assertEquals(initialAmount, cell.count());

        BanknoteCellMemento memento = cell.getMemento();
        Assert.assertEquals(initialAmount, memento.getCount());

        List<Banknote> cash = cell.withdrawAmount(cell.count());
        Assert.assertEquals(initialAmount, cash.size());
        Assert.assertTrue(cell.count() == 0);
        for(Banknote banknote : cash){
            Assert.assertSame(TEST_CURRENCY, banknote.getCurrency());
            Assert.assertEquals(TEST_NOMINAL, banknote.getNominal());
        }

        cell.restore(memento);
        Assert.assertEquals(initialAmount, cell.count());
        cash = cell.withdrawAmount(cell.count());
        Assert.assertEquals(initialAmount, cash.size());
        for(Banknote banknote : cash){
            Assert.assertSame(TEST_CURRENCY, banknote.getCurrency());
            Assert.assertEquals(TEST_NOMINAL, banknote.getNominal());
        }
    }
}
