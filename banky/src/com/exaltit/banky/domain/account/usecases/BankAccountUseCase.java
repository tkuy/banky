package com.exaltit.banky.domain.account.usecases;

import com.exaltit.banky.domain.account.entities.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountUseCase {

    Optional<UUID> createBankAccount(final BankAccount input);

    void deposit(final UUID bankAccountId, long amount);

    void withdraw(final UUID bankAccountId, long amount);

    Optional<BankAccount> getById(final UUID bankAccountId);

    String statement(UUID bankAccountId);
}
