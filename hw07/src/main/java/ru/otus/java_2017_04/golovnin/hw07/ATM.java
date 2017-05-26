package ru.otus.java_2017_04.golovnin.hw07;

import com.sun.istack.internal.NotNull;

import javax.swing.plaf.basic.BasicMenuBarUI;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ATM {
    private List<BanknoteCell> cells;
    private BanknoteCell cellsChain;
    private Currency currency;
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("RUB");

    public ATM(@NotNull Currency currency, @NotNull List<BanknoteCell> cells){
        if(currency == null) this.currency = DEFAULT_CURRENCY;
        else this.currency = currency;
        this.cells = cells.stream().filter(c -> c.getCurrency().equals(currency)).collect(Collectors.toList());
        if(!this.cells.isEmpty()) {
            cellsChain = this.cells.get(0);
            this.cells.stream().skip(1).forEach(c -> cellsChain = cellsChain.link(c));
        }
    }

    public Currency getCurrency(){
        return currency;
    }

    public List<Banknote> putCash(List<Banknote> cash){
        List<Banknote> returnedBanknotes = new LinkedList<>();
        if(cellsChain != null && cash != null)
            if(!cash.isEmpty()){
                while (!cash.isEmpty()){
                    Banknote banknote = cash.remove(cash.size() - 1);
                    Banknote returnedBanknote = cellsChain.put(banknote);
                    if(returnedBanknote != null){
                        returnedBanknotes.add(returnedBanknote);
                    }
                }
            }
        return returnedBanknotes;
    }

    List<Banknote> withdraw(int sum) {
        List<Banknote> cash;
        if(sum > 0){
            cash = cellsChain.withdraw(sum);
        }
        else if(sum == 0) cash = new LinkedList<>();
        else throw new IllegalArgumentException();
        return cash;
    }

    public int getBallance(){
        int result = 0;
        if(cells != null){
            for (BanknoteCell cell : cells
                 ) {
                result += cell.count()*cell.getNominal();
            }
        }
        return result;
    }
}
