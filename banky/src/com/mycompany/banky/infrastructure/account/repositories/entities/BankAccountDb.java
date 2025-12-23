package com.mycompany.banky.infrastructure.account.repositories.entities;

import com.mycompany.banky.domain.account.entities.BankAccount;
import com.mycompany.banky.domain.account.entities.BankAccountFactory;
import com.mycompany.banky.domain.account.entities.BankAccountType;

import java.util.List;
import java.util.UUID;

public record BankAccountDb(UUID id, long allowedOverdraft, long maxAmount, long balance, BankAccountType bankAccountType) {
    public static BankAccountDb fromBankAccountDomain(final BankAccount bankAccount) {
        return new BankAccountDb(bankAccount.getId(), bankAccount.getAllowedOverdraft(), bankAccount.getMaxAmount(), bankAccount.getBalance(), bankAccount.getBankAccountType());
    }

    public static BankAccount fromBankAccountDb(final BankAccountDb bankAccountDb) {
        return BankAccountFactory.createBankAccount(bankAccountDb.id(), bankAccountDb.allowedOverdraft(), bankAccountDb.maxAmount(), bankAccountDb.balance(), bankAccountDb.bankAccountType(), List.of());
    }
}
