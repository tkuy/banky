package com.exaltit.banky.domain.financialtransaction.repositories;

import com.exaltit.banky.domain.account.BankAccount;
import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;

import java.util.List;
import java.util.UUID;

public interface FinancialTransactionRepository {
    UUID create(UUID bankAccountId, FinancialTransaction financialTransaction);

    List<FinancialTransaction> findAllByBankAccountId(UUID bankAccountId);
}
