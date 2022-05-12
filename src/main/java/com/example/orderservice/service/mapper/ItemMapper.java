package com.example.orderservice.service.mapper;

import com.example.orderservice.dto.request.ItemRequestDto;
import com.example.orderservice.dto.response.ItemResponseDto;
import com.example.orderservice.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper implements RequestDtoMapper<ItemRequestDto, Item>,
        ResponseDtoMapper<Item, ItemResponseDto> {
    @Override
    public Item mapToModel(ItemRequestDto requestDto) {
        Item item = new Item();
        item.setModel(requestDto.getModel());
        item.setPrice(requestDto.getPrice());
        item.setDescription(requestDto.getDescription());
        item.setCategory(requestDto.getCategory());
        item.setQuantity(requestDto.getQuantity());
        return item;
    }

    @Override
    public ItemResponseDto mapToDto(Item item) {
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setPrice(item.getPrice());
        responseDto.setModel(item.getModel());
        responseDto.setDescription(item.getDescription());
        responseDto.setCategory(item.getCategory());
        responseDto.setQuantity(item.getQuantity());
        return responseDto;
    }
}
