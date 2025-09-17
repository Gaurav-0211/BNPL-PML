package com.bnpl.controller;

import com.bnpl.model.BNPLTransaction;
import com.bnpl.service.BNPLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bnpl")
public class BNPLController {

    @Autowired
    private BNPLService bnplService;


    // Create a BNPL transaction
    @PostMapping("/transaction")
    public ResponseEntity<BNPLTransaction> createTransaction(
            @RequestParam Long userId,
            @RequestParam Long merchantId,
            @RequestParam Double amount) {
        BNPLTransaction transaction = this.bnplService.createTransaction(userId, merchantId, amount,3);
        return ResponseEntity.ok(transaction);
    }

    // Get transactions by user
    @GetMapping("/transactions/user/{userId}")
    public ResponseEntity<List<BNPLTransaction>> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.bnplService.getTransactionsByUser(userId));
    }

    // Get transactions by merchant
    @GetMapping("/transactions/merchant/{merchantId}")
    public ResponseEntity<List<BNPLTransaction>> getTransactionsByMerchant(@PathVariable Long merchantId) {
        return ResponseEntity.ok(this.bnplService.getTransactionsByMerchant(merchantId));
    }
}
