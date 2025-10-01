package com.example.order.controller;

import com.example.order.model.Order;
import com.example.order.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    public record CreateOrderRequest(
            @NotNull Integer productId,
            @NotNull @Min(1) Integer quantity,
            @NotNull BigDecimal unitPrice) {}

    @GetMapping
    public Flux<Order> all(@RequestParam(value = "productId", required = false) Integer productId) {
        return (productId == null) ? service.getAll() : service.byProduct(productId);
    }

    @GetMapping("/{id}")
    public Mono<Order> byId(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> create(@RequestBody CreateOrderRequest req) {
        return service.create(req.productId(), req.quantity(), req.unitPrice());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
