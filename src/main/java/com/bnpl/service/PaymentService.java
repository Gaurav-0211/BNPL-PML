package com.bnpl.service;

import com.bnpl.model.EMI;

import java.util.List;

public interface PaymentService {

    EMI payEmi(Long emiId);

    void markOverdueEMIs();

    List<EMI> getEmisByTransaction(Long transactionId);

    List<EMI> getPendingEmis();
}
