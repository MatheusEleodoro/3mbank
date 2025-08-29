package com.threembank.infrastructure.persistence.entity;

import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.domain.valueobject.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long number;

    @Column(name = "account_agency")
    private Long agency;

    @Column(name = "user_id",nullable = false,updatable = false)
    private UUID userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private AccountType type;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private AccountStatus status;
    @Column(name = "balance")
    private BigDecimal balance;
}
