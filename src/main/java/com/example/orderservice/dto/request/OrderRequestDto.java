package com.example.orderservice.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    @Min(value = 1)
    private Integer quantity;
    @NotNull
    @Min(value = 1)
    private Long itemId;
}
