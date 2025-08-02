package com.exaltit.banky.domain.financialtransaction.entities;

import java.time.LocalDateTime;

public record FinancialTransaction(LocalDateTime localDateTime, Long amount, Long balance, TransactionTypeEnum transactionType) {

}