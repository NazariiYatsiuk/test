package com.example.orderservice.service.impl;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public boolean isNotValid(Order order) {
        return order.getCreationTime().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(orderRepository.getById(id));
    }

    @Override
    public boolean deleteAllNotValidInCategory(Category category) {
        orderRepository.findAllByItemCategory(category)
                .stream()
                .filter(this::isNotValid)
                .forEach(o -> this.delete(o.getId()));
        return true;
    }
}
