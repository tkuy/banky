package com.mycompany.banky.domain.account.entities;

public enum BankAccountType {

    LIVRET_A_SAVING_ACCOUNT(0, 22950, "Livret A"),
    CURRENT_ACCOUNT(0, Long.MAX_VALUE, "Current account"),
    DEFAULT(0, Long.MAX_VALUE, "Default account");

    final long allowedOverdraft;
    final long maxAmount;
    final String displayName;

    BankAccountType(final long allowedOverdraft, final long maxAmount, final String displayName) {
        this.allowedOverdraft = allowedOverdraft;
        this.maxAmount = maxAmount;
        this.displayName = displayName;
    }
}