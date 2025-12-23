package com.mycompany.banky.domain.financialtransaction.repositories;

import com.mycompany.banky.domain.financialtransaction.entities.FinancialTransaction;

import java.util.List;
import java.util.UUID;

public interface FinancialTransactionRepository {
    UUID create(UUID bankAccountId, FinancialTransaction financialTransaction);

    List<FinancialTransaction> findAllByBankAccountId(UUID bankAccountId);
}
