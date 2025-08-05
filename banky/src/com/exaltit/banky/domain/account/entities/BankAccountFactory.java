package com.exaltit.banky.domain.account.entities;

import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountCreationDto;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountTypeDto;

import java.util.List;
import java.util.UUID;

public class BankAccountFactory {
    public static BankAccount createBankAccount(final BankAccountType bankAccountType) {
        return switch (bankAccountType) {
            case LIVRET_A_SAVING_ACCOUNT -> new BankAccount(BankAccountType.LIVRET_A_SAVING_ACCOUNT.allowedOverdraft, BankAccountType.LIVRET_A_SAVING_ACCOUNT.maxAmount, bankAccountType);
            default -> new BankAccount(BankAccountType.CURRENT_ACCOUNT.allowedOverdraft, BankAccountType.CURRENT_ACCOUNT.maxAmount, bankAccountType);
        };
    }

    public static BankAccount createBankAccount(final long allowedOverdraft, final long maxAmount, final BankAccountType bankAccountType) {
        return new BankAccount(allowedOverdraft, maxAmount, bankAccountType);
    }

    public static BankAccount createBankAccount(final UUID id, final long allowedOverdraft, final long maxAmount, final Long balance, final BankAccountType bankAccountType, final List<FinancialTransaction> financialTransactions) {
        return new BankAccount(id, allowedOverdraft, maxAmount, balance, bankAccountType, financialTransactions);
    }

    public static BankAccount createBankAccount(final BankAccountCreationDto bankAccountCreationDto) {
        final BankAccountType bankAccountType = bankAccountCreationDto.bankAccountType().map(BankAccountTypeDto::toDomain).orElse(null);
        return switch (bankAccountType) {
            case BankAccountType.LIVRET_A_SAVING_ACCOUNT -> {
                if(bankAccountCreationDto.allowedOverdraft().isPresent() || bankAccountCreationDto.maxAmount().isPresent()) {
                    throw new IllegalArgumentException("Can't define allowedOverdraft and maxAmount for this type of account, leave them empty");
                }
                yield createBankAccount(BankAccountType.LIVRET_A_SAVING_ACCOUNT);
            }
            case BankAccountType.CURRENT_ACCOUNT -> {
                final Long allowedOverdraft = bankAccountCreationDto.allowedOverdraft().orElse(BankAccountType.CURRENT_ACCOUNT.allowedOverdraft);
                final Long maxAmount = bankAccountCreationDto.maxAmount().orElse(BankAccountType.CURRENT_ACCOUNT.maxAmount);
                yield createBankAccount(allowedOverdraft, maxAmount, BankAccountType.CURRENT_ACCOUNT);
            }
            case null -> {
                final Long allowedOverdraft = bankAccountCreationDto.allowedOverdraft().orElse(BankAccountType.DEFAULT.allowedOverdraft);
                final Long maxAmount = bankAccountCreationDto.maxAmount().orElse(BankAccountType.DEFAULT.maxAmount);
                yield createBankAccount(allowedOverdraft, maxAmount, BankAccountType.DEFAULT);
            }
            case DEFAULT -> {
                final Long allowedOverdraft = bankAccountCreationDto.allowedOverdraft().orElse(BankAccountType.DEFAULT.allowedOverdraft);
                final Long maxAmount = bankAccountCreationDto.maxAmount().orElse(BankAccountType.DEFAULT.maxAmount);
                yield createBankAccount(allowedOverdraft, maxAmount, BankAccountType.DEFAULT);
            }
        };
    }
}