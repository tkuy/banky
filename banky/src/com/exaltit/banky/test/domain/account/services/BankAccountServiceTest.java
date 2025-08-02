package com.exaltit.banky.test.domain.account.services;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.BankAccountFactory;
import com.exaltit.banky.domain.account.BankAccountType;
import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.entities.TransactionTypeEnum;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.exaltit.banky.test.domain.account.ApplicationContextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class BankAccountServiceTest {

    private final BankAccountRepository bankAccountRepository = ApplicationContextTest.getInstance().getBankAccountRepository();
    private final FinancialTransactionRepository financialTransactionRepository = ApplicationContextTest.getInstance().getFinancialTransactionRepository();
    private final BankAccountService bankAccountService = ApplicationContextTest.getInstance().getBankAccountService();

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
        bankAccountService.createBankAccount(input);
        // WHEN
        bankAccountService.deposit(input.getId(), 15L);
        // THEN
        final Optional<BankAccount> bankAccount = bankAccountService.getById(input.getId());
        final List<FinancialTransaction> financialTransactions = bankAccount.map(BankAccount::getFinancialTransactions).orElse(List.of());
        final FinancialTransaction financialTransaction = financialTransactions.getLast();
        // How to avoid 3 without depending on the result? (date+id)
        Assertions.assertEquals(TransactionTypeEnum.DEPOSIT, financialTransaction.transactionType());
        Assertions.assertEquals(15L, financialTransaction.amount());
        Assertions.assertEquals(15L, financialTransaction.balance());
        Assertions.assertEquals(15, bankAccount.get().getBalance());
    }


    @Test
    void withdraw() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(input);
        bankAccountService.deposit(input.getId(), 15L);
        // WHEN
        bankAccountService.withdraw(input.getId(), 15L);
        // THEN
        final Optional<BankAccount> bankAccount = bankAccountService.getById(input.getId());
        final List<FinancialTransaction> financialTransactions = bankAccount.map(BankAccount::getFinancialTransactions).orElse(List.of());
        final FinancialTransaction financialTransaction = financialTransactions.getLast();
        // How to avoid 3 without depending on the result? (date+id)
        Assertions.assertEquals(TransactionTypeEnum.WITHDRAWAL, financialTransaction.transactionType());
        Assertions.assertEquals(15L, financialTransaction.amount());
        Assertions.assertEquals(0L, financialTransaction.balance());
    }

    @Test
    @DisplayName("Feature 1: Deposit 50, withdraw 40, balance 10")
    void feature1() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(input);
        bankAccountService.deposit(input.getId(), 50L);
        // WHEN
        bankAccountService.withdraw(input.getId(), 40L);
        // THEN
        final Optional<BankAccount> bankAccount = bankAccountService.getById(input.getId());
        // How to avoid 3 without depending on the result? (date+id)
        Assertions.assertEquals(10L, bankAccount.get().getBalance());
    }

    @Test
    @DisplayName("Feature 1: Current account, can't withdraw more than the overdraft")
    void withdrawFail() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(input);
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalStateException.class,  () -> bankAccountService.withdraw(input.getId(), 15L));
    }

    @Test
    @DisplayName("Feature 2: Withdraw will leave balance less than zero")
    void feature2() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(50, 1000, BankAccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(input);
        // WHEN
        bankAccountService.withdraw(input.getId(), 50L);
        // THEN
        final Optional<BankAccount> bankAccount = bankAccountService.getById(input.getId());
        final long actual = bankAccount.get().getBalance();
        Assertions.assertEquals(-50, actual);
    }

    @Test
    @DisplayName("Feature 3: Livret A, Reach the lawful max limit")
    void depositFail() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.LIVRET_A_SAVING_ACCOUNT);
        bankAccountService.createBankAccount(input);
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalStateException.class,  () -> bankAccountService.deposit(input.getId(), 22951));
    }

    @Test
    @DisplayName("Feature 4: Statement with all the transactions for printing tickets")
    void feature4() {
        // GIVEN
        final BankAccount input = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(input);
        bankAccountService.deposit(input.getId(), 50L);
        // WHEN
        bankAccountService.withdraw(input.getId(), 40L);
        // THEN
        final String result = bankAccountService.statement(input.getId());
        final String expectedDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        final String expected = """
                Account type: Current account
                Current balance: 10
                Date       | Transaction | Amount | Balance
                %s | DEPOSIT     | 50     | 50
                %s | WITHDRAWAL  | 40     | 10
                """.formatted(expectedDate, expectedDate);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Feature 4: Statement empty if no bank account")
    void noStatementWhenNoBankAccountId() {
        // GIVEN
        // WHEN
        // THEN
        final String result = bankAccountService.statement(UUID.randomUUID());
        final String expected = "";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void depositFailWhenBankAccountDoesntExist() {
        // GIVEN
        final UUID nonExistingBankId = UUID.randomUUID();
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(nonExistingBankId, 15L));
    }

}