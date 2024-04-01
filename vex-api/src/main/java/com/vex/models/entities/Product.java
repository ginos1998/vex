package com.vex.models.entities;

import com.vex.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
public class Product {
    @Id
    @Column(value = "product_id")
    private Integer productId;

    @Column(value = "branch_id")
    private Integer branchId;

    @Column(value = "category_id")
    private Integer categoryId;

    @Column(value = "name")
    private String name;

    @Column(value = "code")
    private String code;

    @Column(value = "price")
    private BigDecimal price;

    @Column(value = "buy_price")
    private BigDecimal buyPrice;

    @Column(value = "stock")
    private Integer stock;

    @Column(value = "description")
    private String description;

    @Column(value = "iva_rate")
    private BigDecimal ivaRate;

    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    private LocalDateTime updatedAt;

    @Column(value = "enabled")
    private String enabled;

    @Column(value = "supplier_id")
    private Integer supplierId;

    @Column(value = "brand_id")
    private Integer brandId;

    public boolean isEnabled() {
        return enabled.equals(Constants.CHAR_Y);
    }
}
