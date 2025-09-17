package com.bnpl.service.impl;

import com.bnpl.model.BNPLTransaction;
import com.bnpl.model.EMI;
import com.bnpl.model.Merchant;
import com.bnpl.model.User;
import com.bnpl.repository.BNPLTransactionRepository;
import com.bnpl.repository.EMIRepository;
import com.bnpl.repository.MerchantRepository;
import com.bnpl.repository.UserRepository;
import com.bnpl.service.BNPLService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BNPLServiceImpl implements BNPLService {

    @Autowired
    private BNPLTransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private EMIRepository emiRepository;

    @Autowired
    private CreditRiskServiceImpl creditRiskServiceImpl;

    public BNPLTransaction createTransaction(Long userId, Long merchantId, Double amount, Integer tenureMonths) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Merchant merchant = this.merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // credit risk check
        if (!this.creditRiskServiceImpl.isEligible(user, amount)) {
            throw new RuntimeException("User not eligible for BNPL");
        }

        // EMI calculation
        Double emiAmount = amount / tenureMonths;

        BNPLTransaction transaction = BNPLTransaction.builder()
                .amount(amount)
                .tenureMonths(tenureMonths)
                .emiAmount(emiAmount)
                .status(BNPLTransaction.Status.ACTIVE)
                .createdAt(LocalDate.now())
                .user(user)
                .merchant(merchant)
                .build();

        transaction = this.transactionRepository.save(transaction);

        // generate EMIs
        List<EMI> emis = new ArrayList<>();
        for (int i = 1; i <= tenureMonths; i++) {
            EMI emi = EMI.builder()
                    .amount(emiAmount)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .status(EMI.Status.PENDING)
                    .transaction(transaction)
                    .build();
            emis.add(emi);
        }
        this.emiRepository.saveAll(emis);
        transaction.setEmis(emis);

        return transaction;
    }

    public List<BNPLTransaction> getUserTransactions(Long userId) {
        return this.transactionRepository.findByUserId(userId);
    }

    public void closeTransaction(Long transactionId) {
        BNPLTransaction txn = this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        txn.setStatus(BNPLTransaction.Status.CLOSED);
        this.transactionRepository.save(txn);
    }

    public List<BNPLTransaction> getTransactionsByUser(Long userId) {
        return this.transactionRepository.findByUserId(userId);
    }

    public List<BNPLTransaction> getTransactionsByMerchant(Long merchantId) {
        return transactionRepository.findByMerchantId(merchantId);
    }

    public BNPLTransaction getTransactionById(Long transactionId) {
        return this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));
    }



}
