package com.exaltit.banky.infrastructure.account.controllers.dtos;

import com.exaltit.banky.domain.account.entities.BankAccountType;

public enum BankAccountTypeDto {
    LIVRET_A_SAVING_ACCOUNT,
    CURRENT_ACCOUNT,
    DEFAULT;

    public static BankAccountType toDomain(final BankAccountTypeDto bankAccountTypeDto) {
        return switch (bankAccountTypeDto) {
            case BankAccountTypeDto.LIVRET_A_SAVING_ACCOUNT -> BankAccountType.LIVRET_A_SAVING_ACCOUNT;
            case BankAccountTypeDto.CURRENT_ACCOUNT -> BankAccountType.CURRENT_ACCOUNT;
            case BankAccountTypeDto.DEFAULT -> BankAccountType.DEFAULT;
        };
    }
}
