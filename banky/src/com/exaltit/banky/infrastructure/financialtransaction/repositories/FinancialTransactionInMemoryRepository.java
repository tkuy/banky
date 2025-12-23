package com.exaltit.banky.infrastructure.financialtransaction.repositories;

import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.repositories.FinancialTransactionRepository;
import com.exaltit.banky.infrastructure.financialtransaction.repositories.entities.FinancialTransactionDb;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

// NOTE: I think I got too far here, just wanted to reproduce the behaviour of a db
// Because we might want to get access to a specific transaction
// But one Repository would have been enough. Keep it simple stupid got failed here :(
// Imagine a route, it would most likely be /bankaccount/:id/transactions/:id
// Too late, enjoy reading this frustrating code :P
public class FinancialTransactionInMemoryRepository implements FinancialTransactionRepository {
    private final Map<UUID, List<FinancialTransactionDb>> bankAccountIdFinancialTransactions = new ConcurrentHashMap<>();

    @Override
    public UUID create(final UUID bankAccountId, final FinancialTransaction financialTransaction) {
        final FinancialTransactionDb financialTransactionDb = FinancialTransactionDb.fromDomain(financialTransaction);
        final List<FinancialTransactionDb> financialTransactionDbs = Optional.ofNullable(bankAccountIdFinancialTransactions.get(bankAccountId)).map(list -> Stream.concat(list.stream(), Stream.of(financialTransactionDb)).toList()).orElse(List.of(financialTransactionDb));
        bankAccountIdFinancialTransactions.put(bankAccountId, financialTransactionDbs);
        return financialTransaction.id();
    }

    @Override
    public List<FinancialTransaction> findAllByBankAccountId(final UUID bankAccountId) {
        return bankAccountIdFinancialTransactions.getOrDefault(bankAccountId, List.of()).stream().map(FinancialTransactionDb::fromDb).toList();
    }
}
