package com.bnpl.service.impl;

import com.bnpl.dto.BNPLTransactionDto;
import com.bnpl.dto.Response;
import com.bnpl.exception.NoDataExist;
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
import org.modelmapper.ModelMapper;
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

    @Autowired
    private Response response;

    @Autowired
    private ModelMapper mapper;

    // First emi transaction created by user
    @Override
    public Response createTransaction(Long userId, Long merchantId, Double amount, Integer tenureMonths) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NoDataExist("User not found"));
        Merchant merchant = this.merchantRepository.findById(merchantId)
                .orElseThrow(() -> new NoDataExist("Merchant not found"));

        // credit risk check
        if (!this.creditRiskServiceImpl.isEligible(user, amount)) {
            throw new NoDataExist("User not eligible for BNPL");
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

        response.setStatus("SUCCESS");
        response.setMessage("Transaction processed successfully");
        response.setData(this.mapper.map(transaction, BNPLTransactionDto.class));
        response.setStatusCode(201);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Get all transactions of an user API
    @Override
    public Response getUserTransactions(Long userId) {
        List<BNPLTransaction> transactions = this.transactionRepository.findByUserId(userId);

        response.setStatus("SUCCESS");
        response.setMessage("User transaction fetched successfully");
        response.setData(transactions.stream().map((t)->this.mapper.map(t, BNPLTransactionDto.class)));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Close a transaction api
    @Override
    public Response closeTransaction(Long transactionId) {
        BNPLTransaction txn = this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoDataExist("Transaction not found"));
        txn.setStatus(BNPLTransaction.Status.CLOSED);
        BNPLTransaction saved = this.transactionRepository.save(txn);

        response.setStatus("SUCCESS");
        response.setMessage("User transaction closed successfully");
        response.setData(this.mapper.map(saved, BNPLTransactionDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getTransactionsByMerchant(Long merchantId) {
       List<BNPLTransaction> transactions =  this.transactionRepository.findByMerchantId(merchantId);

        response.setStatus("SUCCESS");
        response.setMessage("Merchant transaction fetched successfully");
        response.setData(transactions.stream().map((t)->this.mapper.map(t, BNPLTransactionDto.class)));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    @Override
    public Response getTransactionById(Long transactionId) {
        BNPLTransaction transaction =  this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoDataExist("Transaction not found with id: " + transactionId));

        response.setStatus("SUCCESS");
        response.setMessage("User transaction fetched successfully");
        response.setData(this.mapper.map(transaction, BNPLTransactionDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // get all transaction api
    @Override
    public Response getAllTransaction() {
        List<BNPLTransaction> allTr = this.transactionRepository.findAll();

        response.setStatus("SUCCESS");
        response.setMessage("All transaction fetched successfully");
        response.setData(allTr.stream().map((t)->this.mapper.map(t, BNPLTransactionDto.class)));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }


}
