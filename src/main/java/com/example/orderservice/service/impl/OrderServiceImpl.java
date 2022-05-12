package com.example.orderservice.service.impl;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.ItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Override
    public Order save(Order order) {
        deleteAllNotValidByCategory(order.getItem().getCategory());
        if (order.getQuantity() > order.getItem().getQuantity()) {
            throw new RuntimeException("Not enough items. Available: "
                    + order.getItem().getQuantity());
        }
        itemService.decreaseItemQuantity(itemRepository
                .getById(order.getItem().getId()), order.getQuantity());
        return orderRepository.save(order);
    }

    @Override
    public boolean isNotValid(Order order) {
        return order.getCreationTime().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not delete. Order with id "
                        + id + " does not exist"));
        orderRepository.delete(order);
        Integer quantity = order.getQuantity();
        itemService.increaseItemQuantity(order.getItem(), quantity);
    }

    @Override
    public List<Order> findAllByItemCategory(Category category) {
        return orderRepository.findAllByItemCategory(category);
    }

    @Override
    public void deleteAllNotValidByCategory(Category category) {
        findAllByItemCategory(category)
                .stream()
                .filter(this::isNotValid)
                .forEach(o -> this.delete(o.getId()));
    }
}
