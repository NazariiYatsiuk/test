package com.example.orderservice.service.impl;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private Item galaxyS3;
    private Item iPhone5s;
    private Order order;
    private Order order2;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    private void createMockData() {
        galaxyS3 = new Item();
        galaxyS3.setId(1L);
        galaxyS3.setModel("Galaxy S3");
        galaxyS3.setCategory(Category.PHONES);
        galaxyS3.setPrice(BigDecimal.valueOf(50));
        galaxyS3.setQuantity(5);
        galaxyS3.setDescription("Samsung smartphone");

        iPhone5s = new Item();
        iPhone5s.setId(2L);
        iPhone5s.setModel("iPhone 5s");
        iPhone5s.setCategory(Category.PHONES);
        iPhone5s.setPrice(BigDecimal.valueOf(50));
        iPhone5s.setQuantity(5);
        iPhone5s.setDescription("iPhone 5s");

        order = new Order();
        order.setId(1L);
        order.setItem(galaxyS3);
        order.setQuantity(order.getItem().getQuantity());
        order.setTotalPrice(order.getItem().getPrice().multiply(BigDecimal.valueOf(order.getItem().getQuantity())));

        order2 = new Order();
        order2.setId(2L);
        order2.setItem(galaxyS3);
        order2.setQuantity(order.getItem().getQuantity());
        order2.setTotalPrice(order.getItem().getPrice().multiply(BigDecimal.valueOf(order.getItem().getQuantity())));
    }

    @Test
    public void isNotValid_withValidOrder_Ok() {
        boolean actual = orderService.isNotValid(order);
        Assertions.assertFalse(actual);
    }

    @Test
    public void deleteAllNotValidInCategory_allValidOrders_Ok() {
        Mockito.when(orderRepository.findAllByItemCategory(Category.PHONES)).thenReturn(List.of(order, order2));
        boolean expected = true;
        boolean actual = orderService.deleteAllNotValidInCategory(Category.PHONES);
        Assertions.assertEquals(expected, actual);
    }
}