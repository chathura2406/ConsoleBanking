package com.banking.entity;

import jakarta.persistence.*;   // JPA import
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Account")

public class Account {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    private double balance;

}
