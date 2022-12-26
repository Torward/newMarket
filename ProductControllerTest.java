package com.geekbrains.spring.web.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekbrains.spring.web.core.dto.ProductDto;
import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    public static final String APPLE = "Apple";
    public static final String HUGO = "Hugo";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductsRepository productsRepository;


    @Test
    @Order(2)
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.content.[0].id").value("1"))
                .andExpect(jsonPath("$.content.[0].title").value(APPLE))
                .andExpect(jsonPath("$.content.[0].price").value(1000));
    }

    @Test
    @Order(1)
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(
                                        ProductDto.builder()
                                                .id(1L)
                                                .title(APPLE)
                                                .price(1000)
                                                .build()
                                )))
                .andExpect(status().isOk());
        assertEquals(27L, productsRepository.findAll().size());
    }

}
