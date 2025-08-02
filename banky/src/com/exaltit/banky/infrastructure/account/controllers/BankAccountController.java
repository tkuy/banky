package com.exaltit.banky.infrastructure.account.controllers;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.account.BankAccountFactory;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountCreationDto;

import java.util.Optional;
import java.util.UUID;

//@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(final BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // @PostMapping("/accounts")
    public Optional<UUID> createBankAccount(/*@RequestBody*/ BankAccountCreationDto bankAccountCreationDto) {
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(bankAccountCreationDto);
        return bankAccountService.createBankAccount(bankAccount);
    }
}
