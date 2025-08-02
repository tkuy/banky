package com.exaltit.banky.domain.financialtransaction.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public record FinancialTransaction(UUID id, LocalDateTime localDateTime, Long amount, Long balance, TransactionTypeEnum transactionType) {

}