package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banking.dto.AccountDto;

import java.util.List;
import java.util.Map;



@RestController 
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // --- Create Account API (Updated) ---
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {

        // Kelinma Service ekata DTO eka denawa. Service eken DTO ekakma denawa.
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }

    // --- Get Account API  ---
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // --- Deposit ---
    @PutMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return accountService.deposit(id, amount);
    }


    // --- Withdraw ---
    @PutMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return accountService.withdraw(id, amount);
    }


    // --- Delete Account API ---
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account);

        return "Account Deleted Successfully!";
    }

    // --- Get Account Statement (Transactions) ---
    @GetMapping("/{id}/transactions") // URL: /api/accounts/1/transactions
    public List<Transaction> getAccountTransactions(@PathVariable Long id) {

        return transactionRepository.findByAccountId(id);
    }


}