package com.bnpl.controller;

import com.bnpl.dto.Response;
import com.bnpl.service.BNPLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bnpl")
public class BNPLController {

    @Autowired
    private BNPLService bnplService;


    // Create a BNPL transaction
    @PostMapping("/transaction")
    public ResponseEntity<Response> createTransaction(
            @RequestParam Long userId,
            @RequestParam Long merchantId,
            @RequestParam Double amount) {
        Response response = this.bnplService.createTransaction(userId, merchantId, amount,3);
        return ResponseEntity.ok(response);
    }

    // Get transactions by user
    @GetMapping("/transactions/user/{userId}")
    public ResponseEntity<Response> getTransactionsByUser(@PathVariable Long userId) {
        Response response = this.bnplService.getUserTransactions(userId);
        return ResponseEntity.ok(response);
    }

    // Get transactions by merchant
    @GetMapping("/transactions/merchant/{merchantId}")
    public ResponseEntity<Response> getTransactionsByMerchant(@PathVariable Long merchantId) {
        Response response = this.bnplService.getTransactionsByMerchant(merchantId);
        return ResponseEntity.ok(response);
    }

    // Close transaction
    @PostMapping("/closeTransaction/{id}")
    public ResponseEntity<Response> close(@PathVariable Long id){
        Response response = this.bnplService.closeTransaction(id);
        return ResponseEntity.ok(response);
    }

    // Get transaction by ID
    @GetMapping("/getTransaction/{id}")
    public ResponseEntity<Response> getTById(@PathVariable Long id){
        Response response = this.bnplService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    // Get All transactions
    @GetMapping("/getAllTransaction")
    public ResponseEntity<Response> getAllT(){
        Response response = this.bnplService.getAllTransaction();

        return ResponseEntity.ok(response);
    }

}
