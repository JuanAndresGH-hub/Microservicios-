package com.example.catalog.controller;

import com.example.catalog.model.Product;
import com.example.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products") // <- importante: SIN /api
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> all(@RequestParam(value = "q", required = false) String q) {
        return service.search(q);
    }

    @GetMapping("/{id}")
    public Mono<Product> byId(@PathVariable Integer id) {
        return service.byId(id);
    }

    @PostMapping
    public Mono<Product> create(@Valid @RequestBody Product p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable Integer id, @Valid @RequestBody Product p) {
        return service.update(id, p);
    }

    @PatchMapping("/{id}/stock/{delta}")
    public Mono<Product> patch(@PathVariable Integer id, @PathVariable Integer delta) {
        return service.patchStock(id, delta);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
