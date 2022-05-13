package com.example.orderservice.service.mapper;

import com.example.orderservice.dto.request.OrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    private static Item galaxyS3;
    private static OrderRequestDto requestDto;
    private static Order testOrder;
    @InjectMocks
    private OrderMapper orderMapper;
    @Mock
    private ItemService itemService;

    @BeforeAll
    private static void createMockData() {
        galaxyS3 = new Item();
        galaxyS3.setId(1L);
        galaxyS3.setModel("Galaxy S3");
        galaxyS3.setCategory(Category.PHONES);
        galaxyS3.setPrice(BigDecimal.valueOf(50));
        galaxyS3.setQuantity(5);

        requestDto = new OrderRequestDto();
        requestDto.setItemId(galaxyS3.getId());
        requestDto.setQuantity(2);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setItem(galaxyS3);
        testOrder.setQuantity(3);
        testOrder.setTotalPrice(galaxyS3.getPrice()
                .multiply(BigDecimal.valueOf(testOrder.getQuantity())));
    }

    @Test
    void mapToModel_Ok() {
        Mockito.when(itemService.findById(galaxyS3.getId()))
                .thenReturn(Optional.of(galaxyS3));
        Order order = orderMapper.mapToModel(requestDto);
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(100);
        BigDecimal actualTotalPrice = order.getTotalPrice();
        Integer expectedQuantity = 2;
        Integer actualQuantity = order.getQuantity();
        Item expectedItem = galaxyS3;
        Item actualItem = order.getItem();
        Assertions.assertEquals(expectedTotalPrice, actualTotalPrice);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
        Assertions.assertEquals(expectedItem, actualItem);
    }

    @Test
    void mapToDto_Ok() {
        OrderResponseDto responseDto = orderMapper.mapToDto(testOrder);
        Long expectedId = testOrder.getId();
        Long actualId = responseDto.getId();
        BigDecimal expectedTotalPrice = testOrder.getTotalPrice();
        BigDecimal actualTotalPrice = responseDto.getTotalPrice();
        Integer expectedQuantity = testOrder.getQuantity();
        Integer actualQuantity = responseDto.getQuantity();
        Long expectedItemId = testOrder.getItem().getId();
        Long actualItemId = responseDto.getItemId();
        Assertions.assertEquals(expectedId, actualId);
        Assertions.assertEquals(expectedTotalPrice, actualTotalPrice);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
        Assertions.assertEquals(expectedItemId, actualItemId);
    }
}

