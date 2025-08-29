package com.threembank.domain.entity;

import com.threembank.domain.validation.Validatable;
import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.domain.valueobject.AccountType;
import com.threembank.infrastructure.security.User;
import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.message.ValidationMessage;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Validatable {
    private UUID userId;
    private Long number;
    private Long agency;
    @Builder.Default
    private AccountType type = AccountType.CHECKING;
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;


    @Override
    public boolean validate() {
        validateField(userId, "userId");
        validateField(agency, "agencyNumber");
        validateField(type, "accountType");
        validateField(status, "accountStatus");
        return true;
    }
    public static Account create(UUID userId, AccountType type, Long agency){
        return Account.builder()
                .userId(userId)
                .agency(agency)
                .type(type)
                .status(AccountStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .build();
    }

    private void validateField(Object field, String fieldName) {
        if (field == null) {
            throw new BasicValidationException(getClass(),ValidationMessage.of(fieldName, "is required"));
        }
    }

    public void inactive(){
        this.status = AccountStatus.INACTIVE;
    }
    public void block(){
        this.status = AccountStatus.BLOCKED;
    }
}
