package com.example.catalog.service;

import com.example.catalog.model.Product;
import com.example.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Flux<Product> all() {
        return repo.findAll();
    }

    public Flux<Product> search(String q) {
        if (q == null || q.isBlank()) return repo.findAll();
        return repo.findByNameContainingIgnoreCase(q);
    }

    public Mono<Product> byId(Integer id) {
        return repo.findById(id);
    }

    public Mono<Product> create(Product p) {
        return repo.save(p);
    }

    public Mono<Product> update(Integer id, Product p) {
        return repo.findById(id)
                .flatMap(db -> repo.save(new Product(id, p.name(), p.price(), p.stock())));
    }

    public Mono<Void> delete(Integer id) {
        return repo.deleteById(id);
    }

    public Mono<Product> patchStock(Integer id, Integer delta) {
        return repo.findById(id)
                .flatMap(db -> repo.save(new Product(db.id(), db.name(), db.price(), db.stock() + delta)));
    }
}
