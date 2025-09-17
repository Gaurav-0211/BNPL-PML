package com.bnpl.service.impl;

import com.bnpl.dto.EmiDto;
import com.bnpl.dto.Response;
import com.bnpl.dto.UserDto;
import com.bnpl.exception.NoDataExist;
import com.bnpl.model.EMI;
import com.bnpl.repository.EMIRepository;
import com.bnpl.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private EMIRepository emiRepository;

    @Autowired
    private Response response;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Response payEmi(Long emiId) {
        EMI emi = this.emiRepository.findById(emiId)
                .orElseThrow(() -> new NoDataExist("EMI not found"));

        if (emi.getStatus() == EMI.Status.PAID) {
            throw new NoDataExist("EMI already paid");
        }

        emi.setStatus(EMI.Status.PAID);
        emi.setPaidDate(LocalDate.now());
        EMI saved =  this.emiRepository.save(emi);

        response.setStatus("SUCCESS");
        response.setMessage("EMI paid successfully");
        response.setData(this.mapper.map(saved, EmiDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Mark all emi api overdue
    @Override
    public Response markOverdueEMIs() {
      List<EMI> allEmi = this.emiRepository.findAll();
      for(EMI emi : allEmi) {
            if (emi.getStatus() == EMI.Status.PENDING &&
                    emi.getDueDate().isBefore(LocalDate.now())) {
                emi.setStatus(EMI.Status.OVERDUE);
                emiRepository.save(emi);
            }
        };

        response.setStatus("SUCCESS");
        response.setMessage("All Emi marked overdue successfully");
        response.setData(null);
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;


    }

    // Get all emi of a transaction
    @Override
    public Response getEmisByTransaction(Long transactionId) {
        List<EMI> allEmi =  this.emiRepository.findByTransactionId(transactionId);

        response.setStatus("SUCCESS");
        response.setMessage("All transaction emi fetched successfully");
        response.setData(allEmi.stream().map((emi)-> this.mapper.map(emi, EmiDto.class)).collect(Collectors.toList()));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Get all emi based on status
    @Override
    public Response getPendingEmis(String status) {
        List<EMI> allEmi =  this.emiRepository.findByStatus(status);

        response.setStatus("SUCCESS");
        response.setMessage("all emi based on status fetched successfully");
        response.setData(allEmi.stream().map((emi)-> this.mapper.map(emi, EmiDto.class)).collect(Collectors.toList()));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

}
