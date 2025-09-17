package com.bnpl.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmiDto {
    private Long id;
    private Double amount;
    private LocalDate dueDate;
    private String status; // "PENDING" / "PAID"
}
