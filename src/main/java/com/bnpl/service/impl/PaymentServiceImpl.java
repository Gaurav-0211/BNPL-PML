package com.bnpl.service.impl;

import com.bnpl.model.EMI;
import com.bnpl.repository.EMIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {

    @Autowired
    private EMIRepository emiRepository;

    public EMI payEmi(Long emiId) {
        EMI emi = emiRepository.findById(emiId)
                .orElseThrow(() -> new RuntimeException("EMI not found"));

        if (emi.getStatus() == EMI.Status.PAID) {
            throw new RuntimeException("EMI already paid");
        }

        emi.setStatus(EMI.Status.PAID);
        emi.setPaidDate(LocalDate.now());
        return emiRepository.save(emi);
    }

    public void markOverdueEMIs() {
        emiRepository.findAll().forEach(emi -> {
            if (emi.getStatus() == EMI.Status.PENDING &&
                    emi.getDueDate().isBefore(LocalDate.now())) {
                emi.setStatus(EMI.Status.OVERDUE);
                emiRepository.save(emi);
            }
        });
    }

    public List<EMI> getEmisByTransaction(Long transactionId) {
        return emiRepository.findByTransactionId(transactionId);
    }

    public List<EMI> getPendingEmis() {
        return emiRepository.findByStatus(EMI.Status.PENDING);
    }

}
