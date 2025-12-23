package com.exaltit.banky.infrastructure.account.controllers;

import com.exaltit.banky.domain.account.entities.BankAccount;
import com.exaltit.banky.domain.account.entities.BankAccountFactory;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.domain.account.usecases.BankAccountUseCase;
import com.exaltit.banky.infrastructure.account.controllers.dtos.BankAccountCreationDto;

import java.util.Optional;
import java.util.UUID;

//@RestController
public class BankAccountController {

    private final BankAccountUseCase bankAccountUseCase;

    public BankAccountController(final BankAccountService bankAccountUseCase) {
        this.bankAccountUseCase = bankAccountUseCase;
    }

    // @PostMapping("/accounts")
    public Optional<UUID> createBankAccount(/*@RequestBody*/ BankAccountCreationDto bankAccountCreationDto) {
        final BankAccount bankAccount = BankAccountFactory.createBankAccount(bankAccountCreationDto);
        return bankAccountUseCase.createBankAccount(bankAccount);
    }
}
