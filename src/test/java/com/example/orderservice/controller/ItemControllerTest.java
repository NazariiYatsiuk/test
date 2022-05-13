package com.example.orderservice.controller;

import com.example.orderservice.dto.request.ItemRequestDto;
import com.example.orderservice.model.Category;
import com.example.orderservice.model.Item;
import com.example.orderservice.service.ItemService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
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
class ItemControllerTest {
    private Item mockItem;
    private Item mockItemWithId;

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
        mockItem.setQuantity(3);

        mockItemWithId = new Item();
        mockItemWithId.setId(1L);
        mockItemWithId.setCategory(Category.PHONES);
        mockItemWithId.setPrice(BigDecimal.valueOf(100));
        mockItemWithId.setModel("iPhone 7");
        mockItemWithId.setDescription("Apple iPhone, produced in 2016");
        mockItemWithId.setQuantity(3);
    }

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getWithLowestPriceInCategoryWithAvailableItems_Ok() {
        Category category = Category.PHONES;
        mockItem.setId(1L);
        Mockito.when(itemService
                        .findWithLowestPriceInCategory(category, 3))
                .thenReturn(mockItem);
        RestAssuredMockMvc
                .given()
                .queryParam("quantity", 3)
                .queryParam("category", category)
                .when()
                .get("/items/with-lowest-price")
                .then()
                .statusCode(200)
                .body("category", Matchers.equalTo("PHONES"))
                .body("price", Matchers.equalTo(100))
                .body("model", Matchers.equalTo("iPhone 7"))
                .body("description", Matchers.equalTo("Apple iPhone, produced in 2016"))
                .body("quantity", Matchers.equalTo(3));
    }

    @Test
    void create_Ok() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setQuantity(3);
        requestDto.setCategory(Category.PHONES);
        requestDto.setModel("iPhone 7");
        requestDto.setPrice(BigDecimal.valueOf(100));
        requestDto.setDescription("Apple iPhone, produced in 2016");
        Mockito.when(itemService.save(mockItem))
                .thenReturn(mockItemWithId);
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/items")
                .then()
                .statusCode(200)
                .body("category", Matchers.equalTo("PHONES"))
                .body("price", Matchers.equalTo(100))
                .body("model", Matchers.equalTo("iPhone 7"))
                .body("description", Matchers.equalTo("Apple iPhone, produced in 2016"))
                .body("quantity", Matchers.equalTo(3));
    }
}
