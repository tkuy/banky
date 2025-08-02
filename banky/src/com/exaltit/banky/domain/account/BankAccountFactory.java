package com.exaltit.banky.domain.account;

import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;

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
}