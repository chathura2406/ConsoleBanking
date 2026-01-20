package org.example;

public class Main {
    public static void main(String[] args) {

        // BankAccount acc = new BankAccount(...); // Abstract nisa 'new' ba.

        // 2. POLYMORPHISM
        // Variable eka 'BankAccount' jathiye. Habai Object eka 'Savings'.
        BankAccount acc1 = new SavingsAccount("Nimal", "S001", 5000, 5);
        BankAccount acc2 = new CurrentAccount("Kamal", "C001", 10000, 20000);

        // Dekatama 'withdraw' method eka call karamu.
        // Java automatically dannawa mokakda run karanna oni kiyala.
        acc1.withdraw(2000); // Meka Savings eke widiyata wada karai.
        acc2.withdraw(15000); // Meka Current (Overdraft) widiyata wada karai.
    }
}

