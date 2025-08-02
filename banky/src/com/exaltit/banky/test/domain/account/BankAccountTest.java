package com.exaltit.banky.test.domain.account;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.BankAccountFactory;
import com.exaltit.banky.domain.account.BankAccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BankAccountTest {
    @Test
    @DisplayName("Bank account initialisation should always have 0")
    void createBankAccount() {
        // GIVEN/WHEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);

        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(0, actual);
    }

    @Test
    void deposit() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        bankAccount.deposit(500L);
        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(500L, actual);
    }

    @Test
    void multipleDeposits() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.deposit(500L);
        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(1000L, actual);
    }

    @Test
    void withdraw() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(5L);
        // THEN
        final Long actual = bankAccount.getBalance();
        Assertions.assertEquals(495L, actual);
    }

    @Test
    void multipleWithdraw() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(5L);
        bankAccount.withdraw(5L);
        // THEN
        final Long actual = bankAccount.getBalance();
        Assertions.assertEquals(490L, actual);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawing() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalStateException.class, () -> bankAccount.withdraw(10L));
    }

    @Test
    void shouldThrowExceptionWhenDeposit() {
        //GIVEN
        BankAccount bankAccount = BankAccountFactory.createBankAccount(0, 10, BankAccountType.DEFAULT);
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalStateException.class, () -> bankAccount.deposit(11L));
    }

    @Test
    void bankAccountShouldHaveUniqueId() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        final BankAccount bankAccount2 = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        // THEN
        Assertions.assertNotEquals(bankAccount.getId(), bankAccount2.getId());
    }

    @Test
    @DisplayName("Feature 1: Deposit 500, Withdraw 450, Refuse to withdraw 100, Remains 50")
    void feature1() {
        //GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(450L);
        try {
            bankAccount.withdraw(100L);
        } catch (Exception ignored) {

        }
        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(50L, actual);
    }

    @Test
    @DisplayName("Feature 2: Deposit 500, Withdraw 450, Allow to withdraw 100, Remains 50")
    void feature2() {
        //GIVEN
        BankAccount bankAccount = BankAccountFactory.createBankAccount(50, Long.MAX_VALUE, BankAccountType.DEFAULT);
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(450L);
        bankAccount.withdraw(100L);
        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(-50L, actual);
    }

    @Test
    @DisplayName("Feature 3: Livret A - Refuse negative balance and refuse to exceed 22950")
    void feature3() {
        // GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.LIVRET_A_SAVING_ACCOUNT);
        // WHEN
        try {
            bankAccount.withdraw(1L);
            bankAccount.deposit(22951);
        } catch (Exception ignored) {

        }
        // THEN
        final Long balance = bankAccount.getBalance();
        Assertions.assertEquals(0L, balance);
    }

    @Test
    @DisplayName("Feature 4: Display Compte Courant, balance, operations ordered by date")
    void feature4() {
        // GIVEN
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(BankAccountType.CURRENT_ACCOUNT);
        final String expectedDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        final String expected = """
                Account type: Current account
                Current balance: 10
                Date       | Transaction | Amount | Balance
                %s | DEPOSIT     | 20     | 20
                %s | WITHDRAWAL  | 10     | 10
                """.formatted(expectedDate, expectedDate);
        // FIXME: This is an awful way to do that
        // Can do better by using a clock for the whole application?
        // Mock BankAccount#financialTransactionsOrdered? But will not really test the added transactions
        // WHEN
        bankAccount.deposit(20);
        bankAccount.withdraw(10);
        // THEN
        final String statement = bankAccount.statement();
        Assertions.assertEquals(expected, statement);
    }
}