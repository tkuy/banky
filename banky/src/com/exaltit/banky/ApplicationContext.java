package com.exaltit.banky;

import com.exaltit.banky.domain.account.repositories.BankAccountRepository;
import com.exaltit.banky.domain.account.services.BankAccountService;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.exaltit.banky.infrastructure.account.repositories.BankAccountInMemoryRepository;
import com.exaltit.banky.infrastructure.financialtransaction.FinancialTransactionInMemoryRepository;

public class ApplicationContext {
    private final static ApplicationContext SINGLETON = new ApplicationContext();
    private final FinancialTransactionRepository financialTransactionRepository = new FinancialTransactionInMemoryRepository();
    private final BankAccountRepository bankAccountRepository = new BankAccountInMemoryRepository();
    private final BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, financialTransactionRepository);

    private ApplicationContext() {

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

    public static ApplicationContext getInstance() {
        return SINGLETON;
    }
}
