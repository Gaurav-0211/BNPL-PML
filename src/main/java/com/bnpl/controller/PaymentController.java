package com.bnpl.controller;

import com.bnpl.model.EMI;
import com.bnpl.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Pay a specific EMI
    @PostMapping("/emi/{emiId}/pay")
    public ResponseEntity<EMI> payEmi(@PathVariable Long emiId) {
        EMI updatedEmi = paymentService.payEmi(emiId);
        return ResponseEntity.ok(updatedEmi);
    }

    // Get EMIs for a transaction
    @GetMapping("/transaction/{transactionId}/emis")
    public ResponseEntity<List<EMI>> getEmisByTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(paymentService.getEmisByTransaction(transactionId));
    }

    // Get all pending EMIs
    @GetMapping("/emis/pending")
    public ResponseEntity<List<EMI>> getPendingEmis() {
        return ResponseEntity.ok(paymentService.getPendingEmis());
    }
}
