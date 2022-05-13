package com.example.orderservice.repository;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Sql("/test-scripts/insert-3-phones.sql")
    @Test
    void findAllWithLowestPriceInCategory_getByExistingCategory_Ok() {
        Item iPhone5s = new Item();
        iPhone5s.setId(2L);
        iPhone5s.setModel("iPhone 5s");
        iPhone5s.setCategory(Category.PHONES);
        iPhone5s.setPrice(BigDecimal.valueOf(50));
        iPhone5s.setQuantity(5);
        iPhone5s.setDescription("Eternal classic, produced in 2013 by Apple inc.");

        Item galaxyS3 = new Item();
        galaxyS3.setId(3L);
        galaxyS3.setModel("Galaxy S3");
        galaxyS3.setCategory(Category.PHONES);
        galaxyS3.setPrice(BigDecimal.valueOf(50));
        galaxyS3.setQuantity(5);
        galaxyS3.setDescription("Samsung smartphone");

        List<Item> expected = List.of(iPhone5s, galaxyS3);
        List<Item> actual = itemRepository.findAllWithLowestPriceInCategory(Category.PHONES);
        Assertions.assertTrue(actual.containsAll(expected));
    }

    @Test
    void findAllWithLowestPriceInCategory_wrongCategory_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            itemRepository.findAllWithLowestPriceInCategory(Category.valueOf("cars"));
        });
    }

    @Test
    void findAllWithLowestPriceInCategory_noItemsFromCategory_Ok() {
        List<Item> actual = itemRepository.findAllWithLowestPriceInCategory(Category.PHONES);
        List<Item> expected = Collections.emptyList();
        Assertions.assertEquals(expected, actual);
    }
}