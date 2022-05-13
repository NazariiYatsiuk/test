package com.example.orderservice.service.mapper;

import com.example.orderservice.dto.request.OrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<Order, OrderResponseDto> {
    private final ItemService itemService;

    public OrderMapper(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Order mapToModel(OrderRequestDto requestDto) {
        Order order = new Order();
        Item item = itemService.findById(requestDto.getItemId()).get();
        Integer quantity = requestDto.getQuantity();
        BigDecimal price = item.getPrice().multiply(BigDecimal.valueOf(quantity));
        order.setItem(item);
        order.setQuantity(quantity);
        order.setTotalPrice(price);
        return order;
    }

    @Override
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setItemId(order.getItem().getId());
        responseDto.setQuantity(order.getQuantity());
        responseDto.setTotalPrice(order.getTotalPrice());
        return responseDto;
    }
}
