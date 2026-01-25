package com.banking.service;

import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // Spring Boot ta kiyanawa meka "Service" ekak kiyala
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // --- DEPOSIT LOGIC ---
    public Account deposit(Long id, double amount) {
        // 1. Account eka gannawa
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. Balance eka wadi karanawa
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // 3. TRANSACTION RECORD EKAK HADANAWA (Meka thama aluth kalla)
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT"); // Type eka save karanawa
        transaction.setTimestamp(LocalDateTime.now()); // Current time eka

        transactionRepository.save(transaction); // Transaction Table ekata danawa

        return savedAccount;
    }

    // --- WITHDRAW LOGIC ---
    public Account withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // Transaction Record for Withdraw
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return savedAccount;
    }
}