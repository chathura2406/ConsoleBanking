package com.banking.repository;

import com.banking.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // List wenuwata Page daanawa, ehemama Pageable parameter ekak pass karanawa
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
}