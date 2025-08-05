package com.exaltit.banky.infrastructure.account.repositories;

import com.exaltit.banky.domain.account.entities.BankAccount;
import com.exaltit.banky.infrastructure.account.repositories.entities.BankAccountDb;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BankAccountInMemoryRepository implements BankAccountRepository {
    private final Map<UUID, BankAccountDb> bankAccounts = new ConcurrentHashMap<>();

    @Override
    public Optional<BankAccount> findByBankAccountId(final UUID bankAccountId) {
        return Optional.ofNullable(bankAccounts.get(bankAccountId)).map(BankAccountDb::fromBankAccountDb);
    }


    @Override
    public UUID createBankAccount(final BankAccount bankAccount) {
        final BankAccountDb bankAccountDb = BankAccountDb.fromBankAccountDomain(bankAccount);
        bankAccounts.put(bankAccount.getId(), bankAccountDb);
        return bankAccount.getId();
    }

    @Override
    public void update(final BankAccount bankAccount) {
        final BankAccountDb bankAccountDb = BankAccountDb.fromBankAccountDomain(bankAccount);
        bankAccounts.put(bankAccount.getId(), bankAccountDb);
    }
}
