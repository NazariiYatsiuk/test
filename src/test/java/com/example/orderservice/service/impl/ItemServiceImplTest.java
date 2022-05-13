package com.example.orderservice.service.impl;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.repository.ItemRepository;
import com.example.orderservice.service.OrderService;
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
class ItemServiceImplTest {
    private Item galaxyS3;
    private Item iPhone5s;
    @InjectMocks
    private ItemServiceImpl itemServiceImpl;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderService orderService;


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
    }

    @Test
    void findWithLowestPriceInCategory_between2Items_Ok() {
        Mockito.when(itemRepository.findAllWithLowestPriceInCategory(Category.PHONES)).thenReturn(List.of(galaxyS3, iPhone5s));
        Item actual = itemServiceImpl.findWithLowestPriceInCategory(Category.PHONES, 7);
        Assertions.assertEquals(galaxyS3, actual);
    }

    @Test
    void decreaseItemQuantity_requestedIsMoreThanActual_Ok() {
        Integer expected = 0;
        itemServiceImpl.decreaseItemQuantity(iPhone5s, 7);
        Integer actual = iPhone5s.getQuantity();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void decreaseItemQuantity_requestedIsLessThanActual_Ok() {
        Integer expected = 2;
        itemServiceImpl.decreaseItemQuantity(iPhone5s, 3);
        Integer actual = iPhone5s.getQuantity();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void increaseItemQuantity_Ok() {
        Integer expected = 7;
        itemServiceImpl.increaseItemQuantity(iPhone5s, 2);
        Integer actual = iPhone5s.getQuantity();
        Assertions.assertEquals(expected, actual);
    }
}