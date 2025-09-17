package com.bnpl.repository;

import com.bnpl.model.BNPLTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BNPLTransactionRepository extends JpaRepository<BNPLTransaction, Long> {

    List<BNPLTransaction> findByUserId(Long userId);

    List<BNPLTransaction> findByMerchantId(Long merchantId);
}
