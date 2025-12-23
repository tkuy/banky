package com.mycompany.banky;

import com.mycompany.banky.domain.account.repositories.BankAccountRepository;
import com.mycompany.banky.domain.account.services.BankAccountService;
import com.mycompany.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.mycompany.banky.infrastructure.account.repositories.BankAccountInMemoryRepository;
import com.mycompany.banky.infrastructure.financialtransaction.repositories.FinancialTransactionInMemoryRepository;

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
