package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.aop.LogTimeExecution;
import com.geekbrains.spring.web.core.dto.Cart;
import com.geekbrains.spring.web.core.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductsService productsService;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    @LogTimeExecution
    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    @LogTimeExecution
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    @LogTimeExecution
    public Cart getCurrentCart(String cartKey) {
        log.debug("Получение текущей карзины..");
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    @LogTimeExecution
    public void addToCart(String cartKey, Long productId) {
        log.info("Добавление продукта в карзину..");
        Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт!"));
        execute(cartKey, c -> {
            c.add(product);
        });

        log.info("Продукт успешно добавлен..");
    }

    @LogTimeExecution
    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    @LogTimeExecution
    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> {
            c.remove(productId);
        });
    }

    @LogTimeExecution
    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> {
            c.decrement(productId);
        });
    }

    @LogTimeExecution
    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    @LogTimeExecution
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    @LogTimeExecution
    private void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

}
