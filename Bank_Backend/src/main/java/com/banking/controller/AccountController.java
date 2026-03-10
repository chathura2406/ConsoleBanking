package com.banking.controller;

import com.banking.dto.TransactionDto;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.IdempotencyRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banking.dto.AccountDto;
import com.banking.entity.IdempotencyLog;
import java.time.LocalDateTime;

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

    @Autowired
    private IdempotencyRepository idempotencyRepository;

    // --- Create Account API (Updated) ---
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {

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
    // --- Deposit API with Idempotency ---
    @PutMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(
            @PathVariable Long id,
            @RequestBody Map<String, Double> request,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        if (idempotencyKey != null && idempotencyRepository.existsById(idempotencyKey)) {
            return ResponseEntity.badRequest().body("Duplicate Request! This transaction is already processed.");
        }
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);

        if (idempotencyKey != null) {
            IdempotencyLog log = new IdempotencyLog(idempotencyKey, 200L, LocalDateTime.now());
            idempotencyRepository.save(log);
        }

        return ResponseEntity.ok(accountDto);
    }


    // --- Withdraw ---
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
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
    @GetMapping("/{id}/transactions")
    public ResponseEntity<Page<TransactionDto>> getAccountTransactions(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") int page, // Default page 0 (Palamu pituwa)
            @RequestParam(value = "size", defaultValue = "5") int size  // Default ekaparakata data 5i
    ) {
        Page<TransactionDto> transactions = accountService.getAccountTransactions(id, page, size);
        return ResponseEntity.ok(transactions);
    }


}