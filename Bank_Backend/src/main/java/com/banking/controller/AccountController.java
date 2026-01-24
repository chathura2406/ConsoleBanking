package com.banking.controller;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@RestController // 1. Meken kiyanne meka API ekak kiyala (JSON walin wada karanne).
@RequestMapping("/api/accounts") // 2. Me class ekata enna oni Address eka. (localhost:8080/api/accounts)
public class AccountController {

    @Autowired // 3. Dependency Injection: Spring Boot ta kiyanawa "Mata Repository eke object ekak hadala denna" kiyala.
    private AccountRepository accountRepository;

    // --- Create Account API ---
    @PostMapping // Meka POST request ekak (Data athulata dana ekak).
    public Account createAccount(@RequestBody Account account) {
        // @RequestBody: Postman eken ena JSON data tika Java Object ekak karanawa.

        // Ara magic 'save' method eka call karanna witharai thiyenne!
        return accountRepository.save(account);
    }

    // --- Get Account API (Data එළියට ගැනීම) ---
    @GetMapping("/{id}") // URL එකේ අගට එන ID එක ගන්නවා (Ex: /api/accounts/1)
    public Account getAccountById(@PathVariable Long id) {

        // Repository එකෙන් අහනවා "මේ ID එක තියෙන කෙනෙක් ඉන්නවද?" කියලා.
        // .orElseThrow(...) කියන්නේ: හොයාගන්න බැරි වුනොත් Error එකක් විසි කරන්න කියන එක.
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // --- Deposit API (Salli Danna) ---
    @PutMapping("/{id}/deposit") // URL eka: /api/accounts/1/deposit
    public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {

        // 1. Postman eken ewana 'amount' eka eliyata gannawa
        Double amount = request.get("amount");

        // 2. Account eka Database eken hoyagannawa
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 3. Balance eka wadi karanawa (Parana Balance + Deposit Gana)
        account.setBalance(account.getBalance() + amount);

        // 4. Aye Database ekata Save karanawa (Meken Auto UPDATE wenawa)
        return accountRepository.save(account);
    }
    @PutMapping("/{id}/withdraw") // URL: /api/accounts/1/withdraw
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // MEKA THAMAI WADAGATHMA KALLA (Validation)
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance"); // Salli madi nam Error ekak denawa
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }
    // --- Delete Account API ---
    @DeleteMapping("/{id}") // URL: /api/accounts/1
    public String deleteAccount(@PathVariable Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account); // Delete karana magic method eka

        return "Account Deleted Successfully!";
    }




}