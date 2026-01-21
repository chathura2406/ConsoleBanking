package org.example;

import java.sql.Connection;
import java.sql.SQLException; // Error handling walata

public class Main {
    public static void main(String[] args) {

        System.out.println("=== OOP TEST ===");

        // Abstract nisa 'new BankAccount' ba.
        // Polymorphism use karala object hadanawa.
        BankAccount acc1 = new SavingsAccount("Nimal", "S001", 5000, 5);
        BankAccount acc2 = new CurrentAccount("Kamal", "C001", 10000, 20000);

        acc1.withdraw(2000);  // Savings logic
        acc2.withdraw(15000); // Current logic (Overdraft)

        System.out.println("\n");

        System.out.println("=== DATABASE TEST ===");

        // Me tika aniwa main method eka ATHULE thiyenna oni
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println(" Database oky");

            // Connection eka wada nam, api close karanna oni
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Connection lost");
        }

        AccountDAO dao = new AccountDAO();

// 1. Salli 5000k danna
        dao.deposit("S001", 5000.0);

// 2. Salli 2000k ganna
        dao.withdraw("S001", 2000.0);

// 3. Balance eka check karanna
        dao.getAccountDetails("S001");

    }
}