package com.exaltit.banky.infrastructure.account.repositories;

import com.exaltit.banky.domain.account.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
    Optional<BankAccount> findByBankAccountId(UUID bankAccountId);

    UUID createBankAccount(BankAccount bankAccount);

    void update(BankAccount bankAccount);
}
