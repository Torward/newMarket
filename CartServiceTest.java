package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.CartService;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
/*
* have
* executed
* check
* */
@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        Product product = new Product();
        product.setId(100L);
        product.setTitle("Rocket");
        product.setPrice(2000000);
        Mockito.doReturn(Optional.of(product)).when(productsService).findById(100L);
        cartService.addToCart("test_cart", 100L);
        Mockito.verify(productsService, Mockito.times(1)).findById(ArgumentMatchers.eq(100L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
    }
}
