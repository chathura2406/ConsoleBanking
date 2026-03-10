package com.banking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDto {
    private Long id;

    @NotBlank(message = "Account holder name cannot be empty")
    private String accountHolderName;

    @Min(value = 0, message = "Balance cannot be negative")
    private double balance;

    private String accountType;
}