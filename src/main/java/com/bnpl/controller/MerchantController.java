package com.bnpl.controller;

import com.bnpl.dto.MerchantDto;
import com.bnpl.dto.Response;
import com.bnpl.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    // Register a merchant
    @PostMapping("/addMerchant")
    public ResponseEntity<Response> addMerchant(@RequestBody MerchantDto merchantDto) {
        Response response = this.merchantService.addMerchant(merchantDto);
        return ResponseEntity.ok(response);
    }

    // Get all merchants
    @GetMapping("/GetAllMerchant")
    public ResponseEntity<Response> getAllMerchants() {
        Response response = this.merchantService.getAllMerchant();
        return ResponseEntity.ok(response);
    }

    // Get a merchant by ID
    @GetMapping("/getMerchant/{id}")
    public ResponseEntity<Response> getMerchantById(@PathVariable Long id) {
        Response response = this.merchantService.getMerchantById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMerchant/{id}")
    public ResponseEntity<Response> deleteMerchant(@PathVariable Long id) {
        Response response = this.merchantService.deleteMerchant(id);
        return ResponseEntity.ok(response);
    }

}
