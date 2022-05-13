package com.example.orderservice.service.mapper;

import com.example.orderservice.dto.request.ItemRequestDto;
import com.example.orderservice.dto.response.ItemResponseDto;
import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemMapperTest {
    private static ItemRequestDto requestDto;
    private static Item testItem;
    @InjectMocks
    private ItemMapper itemMapper;

    @BeforeAll
    private static void createMockData() {
        requestDto = new ItemRequestDto();
        requestDto.setCategory(Category.PHONES);
        requestDto.setModel("iPhone 5s");
        requestDto.setPrice(BigDecimal.valueOf(50));
        requestDto.setDescription("Old Apple smartphone");
        requestDto.setQuantity(4);

        testItem = new Item();
        testItem.setCategory(Category.PHONES);
        testItem.setModel("iPhone 5s");
        testItem.setPrice(BigDecimal.valueOf(50));
        testItem.setDescription("Old Apple smartphone");
        testItem.setQuantity(4);
    }

    @Test
    void mapToModel_Ok() {
        Item item = itemMapper.mapToModel(requestDto);
        Category expectedCategory = requestDto.getCategory();
        Category actualCategory = item.getCategory();
        String expectedModel = requestDto.getModel();
        String actualModel = item.getModel();
        BigDecimal expectedPrice = requestDto.getPrice();
        BigDecimal actualPrice = item.getPrice();
        String expectedDescription = requestDto.getDescription();
        String actualDescription = item.getDescription();
        Integer expectedQuantity = requestDto.getQuantity();
        Integer actualQuantity = item.getQuantity();
        Assertions.assertEquals(expectedCategory, actualCategory);
        Assertions.assertEquals(expectedModel, actualModel);
        Assertions.assertEquals(expectedPrice, actualPrice);
        Assertions.assertEquals(expectedDescription, actualDescription);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void mapToDto_Ok() {
        ItemResponseDto responseDto = itemMapper.mapToDto(testItem);
        Category expectedCategory = testItem.getCategory();
        Category actualCategory = responseDto.getCategory();
        String expectedModel = testItem.getModel();
        String actualModel = responseDto.getModel();
        BigDecimal expectedPrice = testItem.getPrice();
        BigDecimal actualPrice = responseDto.getPrice();
        String expectedDescription = testItem.getDescription();
        String actualDescription = responseDto.getDescription();
        Integer expectedQuantity = testItem.getQuantity();
        Integer actualQuantity = responseDto.getQuantity();
        Assertions.assertEquals(expectedCategory, actualCategory);
        Assertions.assertEquals(expectedModel, actualModel);
        Assertions.assertEquals(expectedPrice, actualPrice);
        Assertions.assertEquals(expectedDescription, actualDescription);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }
}