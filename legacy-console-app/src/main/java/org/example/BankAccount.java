package org.example;

public abstract class BankAccount {
    private String accountHolderName ;
    private String accountNumber ;
    protected  double balance;

    // constructor
    public BankAccount( String name  , String accNum  ,double initialBalance  ){
        this.accountHolderName = name ;
        this.accountNumber = accNum ;
        this.balance = initialBalance ;

    }

    public double getBalance () {
        return balance;
    }

    public String getaccountHolderName  () {
        return accountHolderName ;
    }


    public void deposit (double amount ){
        if (amount >0){
        this.balance += amount ;
        System.out.println(amount + "Deposited. Current Balance is " + this.balance  );}
        else {
            System.out.println("Invalid Amount ");
        }

    }

    public abstract void withdraw (double amount );

}
