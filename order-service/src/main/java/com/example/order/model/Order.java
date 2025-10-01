package com.example.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("customer_order")
public record Order(
        @Id Integer id,
        @NotNull Integer productId,
        @NotNull @Min(1) Integer quantity,
        @NotNull BigDecimal total,
        LocalDateTime createdAt
) {}
