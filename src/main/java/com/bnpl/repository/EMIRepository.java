package com.bnpl.repository;

import com.bnpl.model.EMI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EMIRepository extends JpaRepository<EMI, Long> {

    List<EMI> findByTransactionId(Long transactionId);

    List<EMI> findByStatus(String status);

}
