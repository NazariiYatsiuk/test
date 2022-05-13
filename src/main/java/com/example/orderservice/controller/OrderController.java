package com.example.orderservice.controller;

import com.example.orderservice.dto.request.OrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.mapper.OrderMapper;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ItemService itemService;

    public OrderController(OrderService orderService,
                           OrderMapper orderMapper,
                           ItemService itemService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.itemService = itemService;
    }

    @PostMapping
    public OrderResponseDto create(@RequestBody @Valid OrderRequestDto requestDto) {
        Order order = orderMapper.mapToModel(requestDto);
        orderService.deleteAllNotValidInCategory(order.getItem().getCategory());
        if (order.getQuantity() > order.getItem().getQuantity()) {
            throw new RuntimeException("Not enough items. Available: "
                    + order.getItem().getQuantity());
        }
        itemService.decreaseItemQuantity(itemService
                .findById(order.getItem().getId()).get(), order.getQuantity());
        return orderMapper.mapToDto(orderService.save(order));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Order order = orderService.findById(id).get();
        orderService.delete(id);
        itemService.increaseItemQuantity(order.getItem(), order.getQuantity());
    }
}
