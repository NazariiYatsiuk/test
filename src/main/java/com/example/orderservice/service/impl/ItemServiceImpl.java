package com.example.orderservice.service.impl;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.repository.ItemRepository;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item findWithLowestPriceInCategory(Category category, Integer requestedQuantity) {
        orderService.deleteAllNotValidInCategory(category);
        List<Item> items = itemRepository.findAllWithLowestPriceInCategory(category);
        Item item = items.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available items in selected category"));
        Integer itemQuantity = item.getQuantity();
        decreaseItemQuantity(item, requestedQuantity);
        if (requestedQuantity > itemQuantity) {
            item.setQuantity(itemQuantity);
        } else {
            item.setQuantity(requestedQuantity);
        }
        return item;
    }

    public boolean decreaseItemQuantity(Item item, Integer subtractor) {
        Integer itemQuantity = item.getQuantity();
        if (itemQuantity <= subtractor) {
            item.setQuantity(0);
            itemRepository.save(item);
        } else {
            item.setQuantity(itemQuantity - subtractor);
            itemRepository.save(item);
        }
        return true;
    }

    @Override
    public boolean increaseItemQuantity(Item item, Integer addition) {
        item.setQuantity(item.getQuantity() + addition);
        itemRepository.save(item);
        return true;
    }
}
