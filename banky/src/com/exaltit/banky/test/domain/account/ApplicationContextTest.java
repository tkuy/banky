package com.exaltit.banky.test.domain.account;

import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.exaltit.banky.infrastructure.account.controllers.BankAccountController;
import com.exaltit.banky.infrastructure.account.repositories.BankAccountInMemoryRepository;
import com.exaltit.banky.infrastructure.financialtransaction.FinancialTransactionInMemoryRepository;

public class ApplicationContextTest {
    private final static ApplicationContextTest SINGLETON = new ApplicationContextTest();
    private final FinancialTransactionRepository financialTransactionRepository = new FinancialTransactionInMemoryRepository();
    private final BankAccountRepository bankAccountRepository = new BankAccountInMemoryRepository();
    private final BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, financialTransactionRepository);
    private final BankAccountController bankAccountController = new BankAccountController(bankAccountService);

    private ApplicationContextTest() {

    }

    public FinancialTransactionRepository getFinancialTransactionRepository() {
        return financialTransactionRepository;
    }

    public BankAccountRepository getBankAccountRepository() {
        return bankAccountRepository;
    }

    public BankAccountService getBankAccountService() {
        return bankAccountService;
    }

    public BankAccountController getBankAccountController() {
        return bankAccountController;
    }

    public static ApplicationContextTest getInstance() {
        return SINGLETON;
    }
}
