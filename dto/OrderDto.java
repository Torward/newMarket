package com.geekbrains.spring.web.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto>items;
    private Integer totalPrice;
    private String address;
    private String phone;


}
