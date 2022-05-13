package com.example.orderservice.service;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Order;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    boolean isNotValid(Order order);

    Optional<Order> findById(Long id);

    void delete(Long id);

    boolean deleteAllNotValidInCategory(Category category);
}
