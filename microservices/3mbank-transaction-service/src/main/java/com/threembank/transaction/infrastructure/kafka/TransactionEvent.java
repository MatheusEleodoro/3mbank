package com.threembank.transaction.infrastructure.kafka;

import com.threembank.transaction.infrastructure.valueobjects.TransactionStatus;
import com.threembank.transaction.infrastructure.valueobjects.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionEvent(
        String eventId,
        TransactionType eventType,
        String transactionId,
        String accountId,
        BigDecimal amount,
        String currency,
        TransactionStatus status,
        String description,
        LocalDateTime createdAt,
        String performedBy
         ) {}
