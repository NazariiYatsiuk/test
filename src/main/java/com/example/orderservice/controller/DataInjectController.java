package com.example.orderservice.controller;

import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.service.ItemService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class DataInjectController {
    private final ItemService itemService;

    public DataInjectController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String inject() {
        Item iphone7 = new Item();
        iphone7.setModel("7");
        iphone7.setCategory(Category.PHONES);
        iphone7.setPrice(BigDecimal.valueOf(200));
        iphone7.setDescription("Iphone 7");
        iphone7.setQuantity(5);

        Item samsungs8 = new Item();
        samsungs8.setModel("s8");
        samsungs8.setCategory(Category.PHONES);
        samsungs8.setPrice(BigDecimal.valueOf(200));
        samsungs8.setDescription("Samsung Galaxy S8");
        samsungs8.setQuantity(5);

        Item iphone11 = new Item();
        iphone11.setCategory(Category.PHONES);
        iphone11.setPrice(BigDecimal.valueOf(500));
        iphone11.setDescription("Iphone 11");
        iphone11.setModel("11");
        iphone11.setQuantity(5);

        Item iphone12 = new Item();
        iphone12.setModel("12");
        iphone12.setCategory(Category.PHONES);
        iphone12.setPrice(BigDecimal.valueOf(700));
        iphone12.setDescription("Iphone 12");
        iphone12.setQuantity(5);

        Item iphone13 = new Item();
        iphone13.setModel("13");
        iphone13.setCategory(Category.PHONES);
        iphone13.setPrice(BigDecimal.valueOf(900));
        iphone13.setDescription("Iphone 13");
        iphone13.setQuantity(5);

        Item airPods2 = new Item();
        airPods2.setModel("2");
        airPods2.setPrice(BigDecimal.valueOf(150));
        airPods2.setCategory(Category.EARPHONES);
        airPods2.setDescription("Best non-vacuum earphones");
        airPods2.setQuantity(5);

        Item airPodsPro = new Item();
        airPodsPro.setModel("Pro");
        airPodsPro.setPrice(BigDecimal.valueOf(300));
        airPodsPro.setCategory(Category.EARPHONES);
        airPodsPro.setDescription("Best vacuum earphones");
        airPodsPro.setQuantity(5);

        Item baseusCharger = new Item();
        baseusCharger.setModel("GD732");
        baseusCharger.setPrice(BigDecimal.valueOf(10));
        baseusCharger.setCategory(Category.CHARGERS);
        baseusCharger.setDescription("Best chinese charger");
        baseusCharger.setQuantity(5);

        Item appleCharger = new Item();
        appleCharger.setModel("original");
        appleCharger.setPrice(BigDecimal.valueOf(15));
        appleCharger.setCategory(Category.CHARGERS);
        appleCharger.setDescription("Original Apple charger");
        appleCharger.setQuantity(5);

        itemService.save(iphone7);
        itemService.save(samsungs8);
        itemService.save(iphone11);
        itemService.save(iphone12);
        itemService.save(iphone13);
        itemService.save(airPods2);
        itemService.save(airPodsPro);
        itemService.save(appleCharger);
        itemService.save(baseusCharger);
        return "Data was injected";
    }
}
