package com.example.order.service;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) { this.repo = repo; }

    public Flux<Order> getAll() { return repo.findAll(); }

    public Mono<Order> getById(Integer id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Orden %d no encontrada".formatted(id))));
    }

    public Flux<Order> byProduct(Integer productId) { return repo.findByProductId(productId); }

    public Mono<Order> create(Integer productId, Integer quantity, BigDecimal unitPrice) {
        if (quantity == null || quantity < 1) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be >= 1"));
        }
        if (unitPrice == null || unitPrice.signum() < 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "unitPrice must be >= 0"));
        }
        BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity));
        Order o = new Order(null, productId, quantity, total, LocalDateTime.now());
        return repo.save(o);
    }

    public Mono<Void> delete(Integer id) { return repo.deleteById(id); }
}
