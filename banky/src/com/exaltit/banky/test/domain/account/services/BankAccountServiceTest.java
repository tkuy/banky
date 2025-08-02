package com.exaltit.banky.test.domain.account.services;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.BankAccountFactory;
import com.exaltit.banky.domain.account.BankAccountType;
import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.exaltit.banky.infrastructure.account.repositories.BankAccountInMemoryRepository;
import com.exaltit.banky.infrastructure.financialtransaction.FinancialTransactionInMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

class BankAccountServiceTest {

    private final BankAccountRepository bankAccountRepository = new BankAccountInMemoryRepository();
    private final FinancialTransactionRepository financialTransactionRepository = new FinancialTransactionInMemoryRepository();
    private final BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, financialTransactionRepository);

    @Test
    void createBankAccount() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        final Optional<UUID> bankAccountId = bankAccountService.createBankAccount(input);
        // THEN
        if(bankAccountId.isEmpty()) {
            Assertions.fail("Didn't have bank id");
        }
        final Optional<BankAccount> result = bankAccountRepository.findByBankAccountId(bankAccountId.get());
        Assertions.assertEquals(result, Optional.of(input));
    }

    @Test
    void deposit() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        final Optional<UUID> bankAccountId = bankAccountService.createBankAccount(input);
        // WHEN
        bankAccountService.deposit(input.getId(), 15L);
        // THEN
        if(bankAccountId.isEmpty()) {
            Assertions.fail("Didn't have bank id");
        }
        final Optional<BankAccount> result = bankAccountRepository.findByBankAccountId(bankAccountId.get());
        Assertions.assertEquals(result, Optional.of(input));
    }

}