package com.exaltit.banky.account;

import com.exaltit.banky.financialtransaction.FinancialTransaction;
import com.exaltit.banky.financialtransaction.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount {

    private final UUID id;

    private final long allowedOverdraft;

    private final long maxAmount;

    private Long balance;

    private BankAccountType bankAccountType;

    private final List<FinancialTransaction> financialTransactions;


    BankAccount(final long allowedOverdraft, final long maxAmount, final BankAccountType bankAccountType) {
        this.id = UUID.randomUUID();
        this.balance = 0L;
        this.allowedOverdraft = allowedOverdraft;
        this.maxAmount = maxAmount;
        this.financialTransactions = new ArrayList<>();
        this.bankAccountType = bankAccountType;
    }


    public void deposit(final long amount) {
        final long newBalance = this.getBalance() + amount;
        if(newBalance > maxAmount) {
            throw new IllegalStateException("Unauthorised operation: balance exceed max amount");
        }
        this.balance = newBalance;
        final FinancialTransaction newTransaction = new FinancialTransaction(LocalDateTime.now(), amount, newBalance, TransactionTypeEnum.DEPOSIT);
        this.financialTransactions.add(newTransaction);
    }

    public Long computeBalance() {
        return this.balance;
    }

    public void withdraw(final long amount) {
        final long newBalance = this.getBalance() - amount;
        if(newBalance < -allowedOverdraft) {
            throw new IllegalStateException("Unauthorised operation: insufficient balance");
        }
        this.balance = newBalance;
        final FinancialTransaction newTransaction = new FinancialTransaction(LocalDateTime.now(), amount, newBalance, TransactionTypeEnum.WITHDRAWAL);
        this.financialTransactions.add(newTransaction);
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
}
