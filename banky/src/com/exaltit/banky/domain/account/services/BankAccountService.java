package com.exaltit.banky.domain.account.services;

import com.exaltit.banky.domain.account.entities.BankAccount;
import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.account.usecases.BankAccountUseCase;
import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BankAccountService implements BankAccountUseCase {
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
        bankAccountRepository.update(bankAccount);
    }

    public void withdraw(final UUID bankAccountId, long amount) {
        final BankAccount bankAccount = bankAccountRepository.findByBankAccountId(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Bank account not found for id %s".formatted(bankAccountId)));
        final FinancialTransaction financialTransaction = bankAccount.withdraw(amount);
        financialTransactionRepository.create(bankAccountId, financialTransaction);
        bankAccountRepository.update(bankAccount);
    }

    public Optional<BankAccount> getById(final UUID bankAccountId) {
        final Optional<BankAccount> bankAccount = bankAccountRepository.findByBankAccountId(bankAccountId);
        if(bankAccount.isEmpty()) {
            return bankAccount;
        }
        final List<FinancialTransaction> transactions = financialTransactionRepository.findAllByBankAccountId(bankAccountId);
        return bankAccount.map(b -> b.withFinancialTransactions(transactions));
    }

    public String statement(UUID bankAccountId) {
        final Optional<BankAccount> bankAccount = getById(bankAccountId);
        return bankAccount.map(BankAccount::statement).orElse("");
    }
}
