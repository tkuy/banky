package com.exaltit.banky.infrastructure.account.controllers.dtos;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.BankAccountType;

import java.util.Optional;

public record BankAccountCreationDto(Optional<Long> allowedOverdraft, Optional<Long> maxAmount, Optional<BankAccountType> bankAccountType) {
}
