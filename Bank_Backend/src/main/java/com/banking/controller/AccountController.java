package com.banking.controller;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@RestController 
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // --- Create Account API ---
    @PostMapping
    public Account createAccount(@RequestBody Account account) {

        return accountRepository.save(account);
    }

    // --- Get Account API  ---
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {


        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // --- Deposit API (Salli Danna) ---
    @PutMapping("/{id}/deposit") // URL eka: /api/accounts/1/deposit
    public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {


        Double amount = request.get("amount");


        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));


        account.setBalance(account.getBalance() + amount);


        return accountRepository.save(account);
    }
    @PutMapping("/{id}/withdraw") // URL: /api/accounts/1/withdraw
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));


        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }
    // --- Delete Account API ---
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account);

        return "Account Deleted Successfully!";
    }




}