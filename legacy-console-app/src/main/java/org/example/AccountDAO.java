package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AccountDAO {

    // Method to save a new account
    public void createAccount(String accNum, String name, double balance, String type) {
        // 1. SQL Query eka (Values walata '?' danne security ekata)
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 2. '?' thiyena than walata values set kireema
            stmt.setString(1, accNum);  // First '?'
            stmt.setString(2, name);    // Second '?'
            stmt.setDouble(3, balance); // Third '?'
            stmt.setString(4, type);    // Fourth '?'

            // 3. Query eka execute karanna
            int rowsAffected = stmt.executeUpdate(); // Return wenne kee denek add unada kiyala

            if (rowsAffected > 0) {
                System.out.println(" Account Created Successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAccountDetails(String accNum) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accNum);

            // Data ganna nisa executeQuery() use karanawa
            ResultSet rs = stmt.executeQuery();

            // Cursor eka next row ekata geniyanna oni data kiyawanna kalin
            if (rs.next()) {
                String name = rs.getString("holder_name");
                double bal = rs.getDouble("balance");
                System.out.println(" Account Found: " + name + " | Balance: " + bal);
            } else {
                System.out.println("Account not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void transferMoney(String fromAcc, String toAcc, double amount) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            // 1. AUTO SAVE NAWATHWANAWA (Transaction Start)
            conn.setAutoCommit(false);

            // Step A: Withdraw from Sender
            PreparedStatement withdrawStmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setString(2, fromAcc);
            int rows1 = withdrawStmt.executeUpdate();

            // Step B: Deposit to Receiver
            PreparedStatement depositStmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
            depositStmt.setDouble(1, amount);
            depositStmt.setString(2, toAcc);
            int rows2 = depositStmt.executeUpdate();

            // 2. CHECK KARANAWA DEKAMA HARI GIYADA KIYALA
            if (rows1 > 0 && rows2 > 0) {
                conn.commit(); //  Okkoma hari, dan save karanna.
                System.out.println(" Transfer Successful!");
            } else {
                conn.rollback(); //  Mokak hari awul, kalin thibba thathwetama yanna.
                System.out.println(" Transfer Failed! Money Rolled Back.");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // ❌ Error ekak awoth undo karanna.
            } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            // Connection close karanna one (Validation)
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }

    public void deposit(String accNum, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount); // Danna ona gana
            stmt.setString(2, accNum); // Account ID eka

            int rows = stmt.executeUpdate(); // Update karanna

            if (rows > 0) {
                System.out.println(" " + amount + " Deposited Successfully!");
            } else {
                System.out.println(" Deposit Failed! Account ID not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(String accNum, double amount) {
        // Balanna query eke agata dapu condition eka: AND balance >= ?
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount); // Ganna ona gana (Calculation ekata)
            stmt.setString(2, accNum); // Account ID
            stmt.setDouble(3, amount); // Check karanna (Condition ekata)

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println( + amount + " Withdrawn Successfully!");
            } else {
                // Rows update une nattam ekata hethu 2k thiyenna puluwan:
                // 1. Account eka na.
                // 2. Salli madi.
                System.out.println("Withdraw Failed! (Insufficient Balance or Invalid ID)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}