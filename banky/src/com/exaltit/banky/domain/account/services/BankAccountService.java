package com.exaltit.banky.domain.account.services;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;

import java.util.Optional;
import java.util.UUID;

public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final FinancialTransactionRepository financialTransactionRepository;

    public BankAccountService(final BankAccountRepository bankAccountRepository, final FinancialTransactionRepository financialTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.financialTransactionRepository = financialTransactionRepository;
    }


    public Optional<UUID> createBankAccount(final BankAccount input) {
        final UUID id = bankAccountRepository.createBankAccount(input);
        return Optional.of(id);
    }

    public void deposit(final UUID bankAccountId, long amount) {
        final BankAccount bankAccount = bankAccountRepository.findByBankAccountId(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Bank account not found for id %s".formatted(bankAccountId)));
        final FinancialTransaction financialTransaction = bankAccount.deposit(amount);
        financialTransactionRepository.create(bankAccountId, financialTransaction);
    }
}
