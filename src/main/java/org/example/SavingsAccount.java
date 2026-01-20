package org.example;

public class SavingsAccount extends BankAccount{
    private double interestRate ;


    public SavingsAccount (String accountHolderName, String accNum , double initialBalance, double interestRate){

        super (accountHolderName , accNum ,initialBalance );
        this.interestRate = interestRate;

    }

    public void addinterest (){
        double interest = getBalance() * (interestRate /100);
        deposit(interest);
        System.out.println ("Interest added : " + interest );

    }
    @Override
    public void withdraw(double amount) {
        // Savings account wala salli thiyenawanam witharak ganna puluwan (No Overdraft)
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn from Savings: " + amount);
        } else {
            System.out.println("Insufficient funds in Savings Account!");
        }
    }

}
