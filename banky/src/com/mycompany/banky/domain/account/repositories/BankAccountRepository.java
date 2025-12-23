package com.mycompany.banky.domain.account.repositories;

import com.mycompany.banky.domain.account.entities.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
    Optional<BankAccount> findByBankAccountId(UUID bankAccountId);

    UUID createBankAccount(BankAccount bankAccount);

    void update(BankAccount bankAccount);
}
