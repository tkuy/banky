package com.exaltit.banky;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankAccountTest {
    @Test
    @DisplayName("Bank account initialisation should always have 0")
    void createBankAccount() {
        // GIVEN/WHEN
        BankAccount bankAccount = new BankAccount();

        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(0, actual);
    }

    @Test
    void deposit() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        // WHEN
        bankAccount.deposit(500L);
        // THEN
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(500L, actual);
    }

    @Test
    void multipleDeposits() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
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
        BankAccount bankAccount = new BankAccount();
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
        BankAccount bankAccount = new BankAccount();
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(5L);
        bankAccount.withdraw(5L);
        // THEN
        final Long actual = bankAccount.getBalance();
        Assertions.assertEquals(490L, actual);
    }

    @Test
    void shouldThrowExceptionWhenUnauthorisedOperation() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalStateException.class, () -> bankAccount.withdraw(10L));
    }

    @Test
    void bankAccountShouldHaveUniqueId() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        // WHEN
        // THEN
        Assertions.assertNotEquals(bankAccount.getId(), bankAccount2.getId());
    }

    @Test
    @DisplayName("Feature 1: Deposit 500, Withdraw 450, Refuse to withdraw 100, Remains 50")
    void feature1() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        // WHEN
        bankAccount.deposit(500L);
        bankAccount.withdraw(450L);
        // THEN
        Assertions.assertThrows(IllegalStateException.class, () -> bankAccount.withdraw(100L));
        final Long actual = bankAccount.computeBalance();
        Assertions.assertEquals(50L, actual);
    }
}