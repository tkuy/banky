package com.exaltit.banky.account;

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
}