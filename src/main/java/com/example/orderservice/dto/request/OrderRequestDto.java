package com.example.orderservice.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull
    @Min(value = 1)
    private Integer quantity;
    @NotNull
    @Min(value = 1)
    private Long itemId;
}
