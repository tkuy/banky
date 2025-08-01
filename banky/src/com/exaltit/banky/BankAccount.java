package com.exaltit.banky;

import java.util.UUID;

public class BankAccount {

    private final UUID id;

    private final long allowedOverdraft;

    private Long balance;

    public BankAccount() {
        this(0);
    }

    public BankAccount(final long allowedOverdraft) {
        this.id = UUID.randomUUID();
        this.balance = 0L;
        this.allowedOverdraft = allowedOverdraft;
    }


    public void deposit(final long amount) {
        this.balance += amount;
    }

    public Long computeBalance() {
        return this.balance;
    }

    public void withdraw(final long amount) {
        if(this.getBalance() - amount < -allowedOverdraft) {
            throw new IllegalStateException("Unauthorised operation: insufficient balance");
        }
        this.balance -= amount;
    }

    public Long getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }
}
