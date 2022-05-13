package com.example.orderservice.service;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import java.util.Optional;

public interface ItemService {
    Item save(Item item);

    Optional<Item> findById(Long id);

    Item findWithLowestPriceInCategory(Category category, Integer quantity);

    boolean decreaseItemQuantity(Item item, Integer subtractor);

    boolean increaseItemQuantity(Item item, Integer addition);
}
