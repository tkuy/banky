package com.exaltit.banky.test.controller.test;

import com.exaltit.banky.infrastructure.account.controllers.BankAccountController;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountCreationDto;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountTypeDto;
import com.exaltit.banky.test.domain.account.ApplicationContextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

class BankAccountControllerTest {

    private final BankAccountController bankAccountController = ApplicationContextTest.getInstance().getBankAccountController();

    @Test
    @DisplayName("Allowed overdraft 0, Max 1000")
    void createBankAccount() {
        // GIVEN
        final BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto(Optional.of(0L), Optional.of(1000L), Optional.of(BankAccountTypeDto.CURRENT_ACCOUNT));
        // WHEN
        // Of course, I should serialise here the request, then send an HTTP request but I'm not using Spring and want to keep it simple
        // Principle is here though
        final Optional<UUID> bankAccount = bankAccountController.createBankAccount(bankAccountCreationDto);
        // THEN
        // Keep it simple bc I don't want to do the GET. This is an interesting / challenging thing to do
        Assertions.assertTrue(bankAccount.isPresent());
    }

    @Test
    @DisplayName("Refuse to create a Livret A with unlawful parameters")
    void livretACreationFail() {
        // GIVEN
        final BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto(Optional.of(-1L), Optional.of(1000L), Optional.of(BankAccountTypeDto.LIVRET_A_SAVING_ACCOUNT));
        // WHEN
        // THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankAccountController.createBankAccount(bankAccountCreationDto));
    }
}