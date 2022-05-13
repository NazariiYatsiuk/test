package com.example.orderservice.dto.response;

import com.example.orderservice.model.Category;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {
    private String model;
    private BigDecimal price;
    private String description;
    private Category category;
    private Integer quantity;
}
