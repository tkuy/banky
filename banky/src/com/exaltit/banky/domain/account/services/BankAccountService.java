package com.exaltit.banky.domain.account.services;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.repositories.BankAccountRepository;

import java.util.Optional;
import java.util.UUID;

public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(final BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    public Optional<UUID> createBankAccount(final BankAccount input) {
        final UUID id = bankAccountRepository.createBankAccount(input);
        return Optional.of(id);
    }
}
