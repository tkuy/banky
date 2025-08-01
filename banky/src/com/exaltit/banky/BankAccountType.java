package com.exaltit.banky;

public enum BankAccountType {

    LIVRET_A_SAVING_ACCOUNT(0, 22950),
    DEFAULT(0, Long.MAX_VALUE);

    final long allowedOverdraft;
    final long maxAmount;

    BankAccountType(final long allowedOverdraft, final long maxAmount) {
        this.allowedOverdraft = allowedOverdraft;
        this.maxAmount = maxAmount;
    }
}