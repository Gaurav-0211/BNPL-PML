package com.bnpl.controller;

import com.bnpl.dto.Response;
import com.bnpl.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Pay a specific EMI
    @PostMapping("/emi/{emiId}/pay")
    public ResponseEntity<Response> payEmi(@PathVariable Long emiId) {
        Response response = this.paymentService.payEmi(emiId);
        return ResponseEntity.ok(response);
    }

    // Get EMIs for a transaction
    @GetMapping("/transaction/{transactionId}/emis")
    public ResponseEntity<Response> getEmisByTransaction(@PathVariable Long transactionId) {
        Response response = this.paymentService.getEmisByTransaction(transactionId);
        return ResponseEntity.ok(response);
    }

    // Get all pending EMIs
    @GetMapping("/emis/pending")
    public ResponseEntity<Response> getPendingEmis(@PathVariable String status) {
        Response response = this.paymentService.getPendingEmis(status);
        return ResponseEntity.ok(response);
    }

    // Mark all emi to overdue if installment is not paid on time
    @GetMapping("/emis/overdue")
    public ResponseEntity<Response> markOverdue() {
        Response response = this.paymentService.markOverdueEMIs();
        return ResponseEntity.ok(response);
    }
}
