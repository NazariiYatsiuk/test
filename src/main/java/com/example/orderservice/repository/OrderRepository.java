package com.example.orderservice.repository;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByItem(Item item);

    List<Order> findAllByItemCategory(Category category);
}
