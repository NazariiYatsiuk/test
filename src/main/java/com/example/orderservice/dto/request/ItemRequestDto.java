package com.example.orderservice.dto.request;

import com.example.orderservice.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ItemRequestDto {
    @NotNull
    @JsonFormat
    private Category category;
    @NotNull
    private String model;
    @Positive
    private BigDecimal price;
    @Size(max = 200)
    private String description;
    private Integer quantity;
}
