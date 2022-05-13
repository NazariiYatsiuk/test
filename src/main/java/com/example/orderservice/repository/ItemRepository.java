package com.example.orderservice.repository;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "from Item where quantity > 0 and price = "
            + "(select min (price) from Item where quantity > 0 and category = ?1) "
            + "order by model asc")
    List<Item> findAllWithLowestPriceInCategory(Category category);
}
