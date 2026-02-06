package com.banking.repository;

import com.banking.entity.IdempotencyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyRepository extends JpaRepository<IdempotencyLog, String> {

}