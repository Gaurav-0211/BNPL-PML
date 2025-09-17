package com.bnpl.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BNPLTransactionDto {
    private Long id;
    private Double amount;
    private Integer tenureMonths;
    private Double emiAmount;
    private String status; // ACTIVE / CLOSED
    private LocalDate createdAt;

    private UserDto user;
    private MerchantDto merchant;
    private List<EmiDto> emis;
}
