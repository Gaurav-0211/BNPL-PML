package com.bnpl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmiDto {
    private Long id;

    @NotNull(message = "Amount can not be blank")
    private Double amount;

    private LocalDate dueDate;

    private String status; // "PENDING" / "PAID"
}
