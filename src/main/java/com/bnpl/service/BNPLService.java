package com.bnpl.service;

import com.bnpl.model.BNPLTransaction;

import java.util.List;

public interface BNPLService {

    BNPLTransaction createTransaction(Long userId, Long merchantId, Double amount, Integer tenureMonths);

    List<BNPLTransaction> getUserTransactions(Long userId);

    void closeTransaction(Long transactionId);

    List<BNPLTransaction> getTransactionsByUser(Long userId);

    List<BNPLTransaction> getTransactionsByMerchant(Long merchantId);

    BNPLTransaction getTransactionById(Long transactionId);
}