package com.exaltit.banky.domain.account;

import com.exaltit.banky.domain.financialtransaction.entities.FinancialTransaction;
import com.exaltit.banky.domain.financialtransaction.entities.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class BankAccount {

    private final UUID id;

    private final long allowedOverdraft;

    private final long maxAmount;

    private Long balance;

    private BankAccountType bankAccountType;

    private List<FinancialTransaction> financialTransactions;

    BankAccount(final UUID id, final long allowedOverdraft, final long maxAmount, final Long balance, final BankAccountType bankAccountType, final List<FinancialTransaction> financialTransactions) {
        this.id = id;
        this.allowedOverdraft = allowedOverdraft;
        this.maxAmount = maxAmount;
        this.balance = balance;
        this.bankAccountType = bankAccountType;
        this.financialTransactions = financialTransactions;
    }

    BankAccount(final long allowedOverdraft, final long maxAmount, final BankAccountType bankAccountType) {
        this.id = UUID.randomUUID();
        this.balance = 0L;
        this.allowedOverdraft = allowedOverdraft;
        this.maxAmount = maxAmount;
        this.financialTransactions = new ArrayList<>();
        this.bankAccountType = bankAccountType;
    }


    public FinancialTransaction deposit(final long amount) {
        final long newBalance = this.getBalance() + amount;
        if(newBalance > maxAmount) {
            throw new IllegalStateException("Unauthorised operation: balance exceed max amount");
        }
        this.balance = newBalance;
        final FinancialTransaction newTransaction = new FinancialTransaction(UUID.randomUUID(), LocalDateTime.now(), amount, newBalance, TransactionTypeEnum.DEPOSIT);
        this.financialTransactions = Stream.concat(this.financialTransactions.stream(), Stream.of(newTransaction)).toList();
        return newTransaction;
    }

    public Long computeBalance() {
        return this.balance;
    }

    public FinancialTransaction withdraw(final long amount) {
        final long newBalance = this.getBalance() - amount;
        if(newBalance < -allowedOverdraft) {
            throw new IllegalStateException("Unauthorised operation: insufficient balance");
        }
        this.balance = newBalance;
        final FinancialTransaction newTransaction = new FinancialTransaction(UUID.randomUUID(), LocalDateTime.now(), amount, newBalance, TransactionTypeEnum.WITHDRAWAL);
        this.financialTransactions = Stream.concat(this.financialTransactions.stream(), Stream.of(newTransaction)).toList();
        return newTransaction;
    }

    public Long getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    public long getAllowedOverdraft() {
        return allowedOverdraft;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public List<FinancialTransaction> getFinancialTransactions() {
        return financialTransactions;
    }

    public BankAccount withFinancialTransactions(final List<FinancialTransaction> financialTransactions) {
        return new BankAccount(this.id,
        this.allowedOverdraft,
        this.maxAmount,
        this.balance,
        this.bankAccountType,
        financialTransactions);
    }

    public List<FinancialTransaction> financialTransactionsOrdered() {
        return this.financialTransactions.stream().sorted((t1, t2) -> {
            if (t1.localDateTime().isBefore(t2.localDateTime())) {
                return -1;
            } else if (t1.localDateTime().isAfter(t2.localDateTime())) {
                return 1;
            } else {
                return 0;
            }
        }).toList();
    }

    public String statement() {
        final StringBuilder stringBuilder = new StringBuilder();
        final String accountType = "Account type: %s".formatted(bankAccountType.displayName);
        final String currentBalance = "Current balance: %s".formatted(this.getBalance());
        final String header = "Date       | Transaction | Amount | Balance";

        stringBuilder.append(accountType).append("\n").append(currentBalance).append("\n").append(header).append("\n");
        financialTransactionsOrdered().stream().forEach(transaction -> {
            String lineFormat;
            if(transaction.transactionType() == TransactionTypeEnum.WITHDRAWAL) {
                lineFormat = "%s | %s  | %s     | %s";
            } else {
                lineFormat = "%s | %s     | %s     | %s";
            }
            final String newLine = lineFormat.formatted(transaction.localDateTime().format(DateTimeFormatter.ISO_DATE), transaction.transactionType(), transaction.amount(), transaction.balance());
            stringBuilder.append(newLine).append("\n");
        });
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final BankAccount that = (BankAccount) object;
        return allowedOverdraft == that.allowedOverdraft && maxAmount == that.maxAmount && Objects.equals(id, that.id) && Objects.equals(balance, that.balance) && bankAccountType == that.bankAccountType && Objects.equals(financialTransactions, that.financialTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, allowedOverdraft, maxAmount, balance, bankAccountType, financialTransactions);
    }
}
