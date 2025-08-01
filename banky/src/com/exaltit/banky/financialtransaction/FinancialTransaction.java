package com.exaltit.banky.financialtransaction;

import java.time.LocalDateTime;

public record FinancialTransaction(LocalDateTime localDateTime, Long amount, Long balance, TransactionTypeEnum transactionType) {

}