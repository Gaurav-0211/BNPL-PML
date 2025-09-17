package com.bnpl.service;

import com.bnpl.dto.MerchantDto;
import com.bnpl.dto.Response;

public interface MerchantService {

    Response addMerchant(MerchantDto dto);

    Response getAllMerchant();

    Response getMerchantById(Long id);

    Response deleteMerchant(Long id);
}
