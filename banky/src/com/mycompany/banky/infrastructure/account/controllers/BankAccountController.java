package com.mycompany.banky.infrastructure.account.controllers;

import com.mycompany.banky.domain.account.entities.BankAccount;
import com.mycompany.banky.domain.account.entities.BankAccountFactory;
import com.mycompany.banky.domain.account.services.BankAccountService;
import com.mycompany.banky.domain.account.usecases.BankAccountUseCase;
import com.mycompany.banky.infrastructure.account.controllers.dtos.BankAccountCreationDto;

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
