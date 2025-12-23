package com.mycompany.banky.infrastructure.account.controllers.dtos;

import java.util.Optional;

public record BankAccountCreationDto(Optional<Long> allowedOverdraft, Optional<Long> maxAmount, Optional<BankAccountTypeDto> bankAccountType) {
}
