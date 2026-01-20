package org.example;

public class CurrentAccount extends BankAccount{
    private double overdraftLimit ;


    public CurrentAccount ( String name , String accNum , double initialBalance , double interestRate ){
        super(name , accNum, initialBalance);
        this.overdraftLimit = overdraftLimit ;

    }

   @Override

    public void withdraw (double amount ){
        if (getBalance() +overdraftLimit <= amount ) {
            balance -= amount;
            System.out.println(amount + " has been deposited to the account " + balance);
        } else {
            System.out.println("Insufficient funds to deposit to the account " + balance);
        }
   }

}
