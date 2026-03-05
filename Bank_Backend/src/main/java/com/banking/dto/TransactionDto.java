package com.banking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private Long accountId;
    private double amount;
    private String transactionType;
    private LocalDateTime timestamp;
}