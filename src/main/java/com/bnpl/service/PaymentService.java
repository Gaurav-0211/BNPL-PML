package com.bnpl.service;

import com.bnpl.dto.Response;

public interface PaymentService {

    Response payEmi(Long emiId);

    Response markOverdueEMIs();

   Response getEmisByTransaction(Long transactionId);

    Response getPendingEmis();

    Response getEmiOfUser(Long id);
}
