package com.exaltit.banky.infrastructure.financialtransaction.entities;

import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.entities.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record FinancialTransactionDb(UUID id, LocalDateTime localDateTime, Long amount, Long balance, TransactionTypeEnum transactionType) {
    public static FinancialTransactionDb fromDomain(final FinancialTransaction financialTransaction) {
        return new FinancialTransactionDb(financialTransaction.id(), financialTransaction.localDateTime(), financialTransaction.amount(), financialTransaction.balance(), financialTransaction.transactionType());
    }

    public static FinancialTransaction fromDb(final FinancialTransactionDb financialTransactionDb) {
        return new FinancialTransaction(financialTransactionDb.id(), financialTransactionDb.localDateTime(), financialTransactionDb.amount(), financialTransactionDb.balance(), financialTransactionDb.transactionType());
    }
}
