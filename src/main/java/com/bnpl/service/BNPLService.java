package com.bnpl.service;

import com.bnpl.dto.Response;

public interface BNPLService {

    Response createTransaction(Long userId, Long merchantId, Double amount, Integer tenureMonths);

    Response getUserTransactions(Long userId);

    Response closeTransaction(Long transactionId);

    Response getTransactionsByMerchant(Long merchantId);

    Response getTransactionById(Long transactionId);

    Response getAllTransaction();

}