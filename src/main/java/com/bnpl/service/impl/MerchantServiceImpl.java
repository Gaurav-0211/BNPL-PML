package com.bnpl.service.impl;

import com.bnpl.dto.MerchantDto;
import com.bnpl.dto.Response;
import com.bnpl.exception.NoDataExist;
import com.bnpl.model.Merchant;
import com.bnpl.repository.MerchantRepository;
import com.bnpl.service.MerchantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Response response;

    @Autowired
    private ModelMapper mapper;

    // API to add merchant
    @Override
    public Response addMerchant(MerchantDto dto) {
        Merchant merchant = this.mapper.map(dto, Merchant.class);

        Merchant saved = this.merchantRepository.save(merchant);

        response.setStatus("SUCCESS");
        response.setMessage("Merchant added successfully");
        response.setData(this.mapper.map(saved, MerchantDto.class));
        response.setStatusCode(201);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Get all API to fetch all merchants
    @Override
    public Response getAllMerchant() {
        List<Merchant> merchants = this.merchantRepository.findAll();

        response.setStatus("SUCCESS");
        response.setMessage("All merchant fetched successfully");
        response.setData(merchants.stream().map((merchant)-> this.mapper.map(merchant, MerchantDto.class)).collect(Collectors.toList()));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Get Merchant by ID API
    @Override
    public Response getMerchantById(Long id) {
        Merchant merchant = this.merchantRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No merchant found with given ID"));

        response.setStatus("SUCCESS");
        response.setMessage("Merchant fetched successfully");
        response.setData(this.mapper.map(merchant, MerchantDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }

    // Delete merchant API
    @Override
    public Response deleteMerchant(Long id) {
        Merchant merchant = this.merchantRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No merchant found with given ID"));

        this.merchantRepository.delete(merchant);
        response.setStatus("SUCCESS");
        response.setMessage("Merchant deleted successfully");
        response.setData(null);
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");

        return response;
    }
}
