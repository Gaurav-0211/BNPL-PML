package com.bnpl.service.impl;

import com.bnpl.model.User;
import com.bnpl.service.CreditRiskService;
import org.springframework.stereotype.Service;

@Service
public class CreditRiskServiceImpl implements CreditRiskService {

    // Simple rule-based engine
    public boolean isEligible(User user, Double requestedAmount) {
        if (user.getCreditScore() == null) return false;

        // Example rules:
        // - Credit score must be >= 650
        // - Max transaction limit = creditScore * 100
        if (user.getCreditScore() < 650) return false;

        double maxLimit = user.getCreditScore() * 100;
        return requestedAmount <= maxLimit;
    }
}
