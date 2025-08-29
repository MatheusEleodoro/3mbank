package com.threembank.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.domain.valueobject.AccountType;
import com.threembank.interfaces.deserializer.AccountMaskSerializer;
import com.threembank.interfaces.deserializer.MaskAgencySerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


public record AccountResponse(
        @JsonProperty("user")
        UUID userId,
        @JsonProperty("account")
        @JsonSerialize(using = AccountMaskSerializer.class)
        Long number,
        @JsonProperty("agency")
        @JsonSerialize(using = MaskAgencySerializer.class)
        Long agency,
        AccountType type,
        AccountStatus status,
        BigDecimal balance) implements Serializable {}
