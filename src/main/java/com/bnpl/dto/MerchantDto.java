package com.bnpl.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
    private Long id;

    @NotEmpty(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be in range 2 - 20 characters")
    private String name;

    private String settlementAccount;  // account where NBFC settles payments

}
