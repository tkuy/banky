package com.exaltit.banky.domain.financialtransaction.repositories;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;

public interface FinancialTransactionRepository {
    BankAccount create(FinancialTransaction financialTransaction);
}
