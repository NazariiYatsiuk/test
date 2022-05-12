package com.example.orderservice.controller;

import com.example.orderservice.dto.request.ItemRequestDto;
import com.example.orderservice.dto.response.ItemResponseDto;
import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.mapper.ItemMapper;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    public ItemResponseDto create(@RequestBody @Valid ItemRequestDto requestDto) {
        Item item = itemMapper.mapToModel(requestDto);
        return itemMapper.mapToDto(itemService.add(item));
    }

    @GetMapping("/{category}/with-lowest-price")
    public ItemResponseDto getWithLowestPriceInCategory(@PathVariable String category,
                                                        @RequestParam Integer quantity) {
        Item item = itemService
                .findWithLowestPriceInCategory(Category
                        .valueOf(category.toUpperCase()), quantity);
        return itemMapper.mapToDto(item);
    }
}
