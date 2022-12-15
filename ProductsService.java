package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.aop.LogTimeExecution;
import com.geekbrains.spring.web.core.dto.ProductDto;
import com.geekbrains.spring.web.core.entities.Product;

import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    @LogTimeExecution
    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 6));
    }
    @LogTimeExecution
    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }
    @LogTimeExecution
    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }
    @LogTimeExecution
    public Product save(Product product) {
        return productsRepository.save(product);
    }
    @LogTimeExecution
    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }
}
