package com.example.orderservice.service;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Order;
import java.util.List;

public interface OrderService {
    Order save(Order order);

    boolean isNotValid(Order order);

    List<Order> findAllByItemCategory(Category category);

    void delete(Long id);

    void deleteAllNotValidByCategory(Category category);
}
