package ru.otus.java_2017_04.golovnin.hw07;


import java.util.*;
import java.util.stream.Collectors;

public class ATM implements ATMInterface{
    private List<BanknoteCell> cells;
    private BanknoteCell cellsChain;
    private Currency currency;
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("RUB");

    public ATM(Currency currency, List<BanknoteCell> cells){
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
        {
            if(!cash.isEmpty()){
                for (Banknote banknote:cash
                     ) {
                    Banknote returnedBanknote = cellsChain.put(banknote);
                    if(returnedBanknote != null){
                        returnedBanknotes.add(returnedBanknote);
                    }
                }
            }
        }
        return returnedBanknotes;
    }

    public List<Banknote> withdraw(int sum) {
        List<Banknote> cash;
        if(sum > 0){
            cash = cellsChain.withdraw(sum);
        }
        else if(sum == 0) cash = Collections.EMPTY_LIST;
        else throw new IllegalArgumentException();
        return cash;
    }

    public int getBalance(){
        int result = 0;
        if(cells != null){
            for (BanknoteCell cell : cells
                 ) {
                result += cell.count()*cell.getNominal();
            }
        }
        return result;
    }

    public ATMMemento getMemento(){
        Map<BanknoteCell, BanknoteCellMemento> cellMementos = new HashMap<BanknoteCell, BanknoteCellMemento>(cells.size()*2);
        cells.forEach(c -> cellMementos.put(c, c.getMemento()));
        return new ATMMemento(cellMementos);
    }

    public void restore(ATMMemento memento){
        Map<BanknoteCell, BanknoteCellMemento> mementoMap = memento.getMementos();
        cells.forEach(c -> {
            c.restore(mementoMap.get(c));
        });
    }
}
