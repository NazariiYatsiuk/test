package com.example.orderservice.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private BigDecimal totalPrice;
    private Integer quantity;
    private Long itemId;
}
