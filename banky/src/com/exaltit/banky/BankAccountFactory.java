package com.exaltit.banky;

public class BankAccountFactory {
    public static BankAccount createBankAccount(final BankAccountType bankAccountType) {
        return switch (bankAccountType) {
            case LIVRET_A_SAVING_ACCOUNT -> new BankAccount(BankAccountType.LIVRET_A_SAVING_ACCOUNT.allowedOverdraft, BankAccountType.LIVRET_A_SAVING_ACCOUNT.maxAmount);
            default -> new BankAccount(BankAccountType.DEFAULT.allowedOverdraft, BankAccountType.DEFAULT.maxAmount);
        };
    }

    public static BankAccount createBankAccount(final long allowedOverdraft, final long maxAmount) {
        return new BankAccount(allowedOverdraft, maxAmount);
    }
}