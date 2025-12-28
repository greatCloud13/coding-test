package com.seowon.coding.domain.dto;

import com.seowon.coding.domain.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class OrderCreateDto {

    @NotNull(message = "customer info required")
    @NotBlank(message = "customer info required")
    private String customerName;

    @NotNull(message = "customer info required")
    @NotBlank(message = "customer info required")
    private String customerEmail;

    @NotBlank(message = "orderReqs invalid")
    @NotNull(message = "orderReqs invalid")
    private List<Long> productIds;

    @NotBlank(message = "orderReqs invalid")
    @NotNull(message = "orderReqs invalid")
    private List<Integer> quantities;
}
