package com.vex.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Table("product")
public class Product {
    @Id
    @Column(value = "product_id")
    private Long productId;

    @Column(value = "product_name")
    private String productName;

    @Column(value = "short_name")
    private String shortName;

    @Column(value = "price")
    private BigDecimal price;

}
