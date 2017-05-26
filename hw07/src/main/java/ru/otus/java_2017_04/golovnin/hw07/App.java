package ru.otus.java_2017_04.golovnin.hw07;

import java.util.Currency;

public class App
{
    private static final Currency operateCurrency = Currency.getInstance("RUB");

    public static void main( String[] args )
    {
        int[] nominals = {50, 100, 500, 1000, 5000};
        BanknoteFactory banknoteFactory = new BanknoteFactory(operateCurrency, nominals);
        DepartmentInterface department = new Department();

        ATMInterface atm1 = ATMFactory.createATM(banknoteFactory, ATMFactory.ATM_TYPE.TYPE1);
        ATMInterface atm2 = ATMFactory.createATM(banknoteFactory, ATMFactory.ATM_TYPE.TYPE2);
        department.addATM(atm1);
        department.addATM(atm2);

        System.out.println("Department ATMs ballance: " + department.getATMsBalance() + " " + operateCurrency.getSymbol());

        atm1.withdraw(atm1.getBalance());
        atm2.withdraw(atm2.getBalance());
        System.out.println("Department ATMs ballance after withdraw: " + department.getATMsBalance() + " " + operateCurrency.getSymbol());

        department.ATMsRestore();
        System.out.println("Department ATMs ballance after restore: " + department.getATMsBalance() + " "  + operateCurrency.getSymbol());

    }


}
