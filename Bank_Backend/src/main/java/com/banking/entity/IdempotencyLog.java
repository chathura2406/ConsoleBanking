package com.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotency_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyLog {

    @Id // Me key eka duplicate wenna ba
    private String idempotencyKey;

    private Long responseStatus; // 200 (OK) da kiyala mathaka thiyaganna
    private LocalDateTime createdTime;
}