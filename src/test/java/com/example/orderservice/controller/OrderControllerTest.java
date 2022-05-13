package com.example.orderservice.controller;

import com.example.orderservice.dto.request.OrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.ItemService;
import com.example.orderservice.service.OrderService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private Item mockItem;
    private Item mockItemWithId;
    private Order mockOrder;
    private Order mockOrderWithId;
    private OrderRequestDto requestDto;
    private OrderResponseDto responseDto;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void createMockItem() {
        mockItem = new Item();
        mockItem.setCategory(Category.PHONES);
        mockItem.setPrice(BigDecimal.valueOf(100));
        mockItem.setModel("iPhone 7");
        mockItem.setDescription("Apple iPhone, produced in 2016");
        mockItem.setQuantity(5);

        mockItemWithId = new Item();
        mockItemWithId.setId(1L);
        mockItemWithId.setCategory(Category.PHONES);
        mockItemWithId.setPrice(BigDecimal.valueOf(100));
        mockItemWithId.setModel("iPhone 7");
        mockItemWithId.setDescription("Apple iPhone, produced in 2016");
        mockItemWithId.setQuantity(5);

        requestDto = new OrderRequestDto();
        requestDto.setItemId(mockItemWithId.getId());
        requestDto.setQuantity(3);

        mockOrder = new Order();
        mockOrder.setItem(mockItemWithId);
        mockOrder.setQuantity(requestDto.getQuantity());
        mockOrder.setTotalPrice(mockItemWithId.getPrice()
                .multiply(BigDecimal.valueOf(mockOrder.getQuantity())));

        mockOrderWithId = new Order();
        mockOrderWithId.setId(1L);
        mockOrderWithId.setItem(mockItemWithId);
        mockOrderWithId.setQuantity(requestDto.getQuantity());
        mockOrderWithId.setTotalPrice(mockItemWithId.getPrice()
                .multiply(BigDecimal.valueOf(mockOrderWithId.getQuantity())));

        responseDto = new OrderResponseDto();
        responseDto.setId(mockOrderWithId.getId());
        responseDto.setItemId(mockOrderWithId.getItem().getId());
        responseDto.setQuantity(mockOrderWithId.getQuantity());
        responseDto.setTotalPrice(mockOrderWithId.getTotalPrice());
    }

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void create_Ok() {
        Mockito.when(itemService.findById(1L))
                .thenReturn(Optional.ofNullable(mockItemWithId));
        Mockito.when(orderService.save(mockOrder))
                .thenReturn(mockOrderWithId);
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/orders")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("itemId", Matchers.equalTo(1))
                .body("quantity", Matchers.equalTo(3))
                .body("totalPrice", Matchers.equalTo(300))
                .body("id", Matchers.equalTo(1));
    }

    @Test
    public void delete_Ok() {
        Mockito.when(orderService.findById(1L)).thenReturn(Optional.of(mockOrderWithId));
        RestAssuredMockMvc
                .when()
                .delete("/orders/1")
                .then()
                .statusCode(200);
    }
}