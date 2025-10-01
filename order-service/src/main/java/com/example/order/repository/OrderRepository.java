package com.example.order.repository;

import com.example.order.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
    Flux<Order> findByProductId(Integer productId);
}
