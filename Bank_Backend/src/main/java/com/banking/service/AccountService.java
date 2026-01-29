package com.banking.service;

import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.banking.dto.AccountDto;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // --- DEPOSIT LOGIC (Updated to return DTO) ---
    @Transactional
    public AccountDto deposit(Long id, double amount) {
        // 1. Account eka gannawa
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. Balance eka wadi karanawa
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // 3. Transaction Record
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        // 4. CONVERT TO DTO (Meka thama aluth kalla)
        AccountDto accountDto = new AccountDto();
        accountDto.setId(savedAccount.getId());
        accountDto.setAccountHolderName(savedAccount.getAccountHolderName());
        accountDto.setBalance(savedAccount.getBalance());

        return accountDto; // Entity eka nemei, DTO eka yawanawa
    }

    // --- WITHDRAW LOGIC (Updated to return DTO) ---
    @Transactional
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // Transaction Record
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        // 4. CONVERT TO DTO
        AccountDto accountDto = new AccountDto();
        accountDto.setId(savedAccount.getId());
        accountDto.setAccountHolderName(savedAccount.getAccountHolderName());
        accountDto.setBalance(savedAccount.getBalance());

        return accountDto;
    }

    // --- Create Account (Meka kalin wagema thiyanna) ---
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setBalance(accountDto.getBalance());

        Account savedAccount = accountRepository.save(account);

        AccountDto savedDto = new AccountDto();
        savedDto.setId(savedAccount.getId());
        savedDto.setAccountHolderName(savedAccount.getAccountHolderName());
        savedDto.setBalance(savedAccount.getBalance());

        return savedDto;
    }
}