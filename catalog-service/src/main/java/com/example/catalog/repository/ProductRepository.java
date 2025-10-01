package com.example.catalog.repository;

import com.example.catalog.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByNameContainingIgnoreCase(String q);
}
