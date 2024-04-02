package vex.batch.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "description")
    private String description;

    @Column(name = "iva_rate")
    private BigDecimal ivaRate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "enabled")
    private String enabled;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "brand_id")
    private Integer brandId;
    

    public static String[] getCsvHeaders() {
        return new String[] {
            "productName",
            "shortName",
            "productPrice"
        };
    }
}
