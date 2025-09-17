package com.bnpl.service;

import com.bnpl.model.User;

public interface CreditRiskService {

    boolean isEligible(User user, Double requestedAmount);

}
