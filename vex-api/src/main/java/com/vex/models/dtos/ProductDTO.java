package com.vex.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.vex.utils.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @JsonView(Views.Public.class)
    private Integer productId;
    private Integer branchId;
    private Integer categoryId;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String code;
    @JsonView(Views.Public.class)
    private BigDecimal price;
    @JsonView(Views.Public.class)
    private BigDecimal buyPrice;
    @JsonView(Views.Public.class)
    private Integer stock;
    @JsonView(Views.Public.class)
    private String description;
    @JsonView(Views.Public.class)
    private BigDecimal ivaRate;
    @JsonView(Views.Public.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private String enabled;
    private Integer supplierId;
    private Integer brandId;

    public static Flux<ProductDTO> fromRow(List<Map<String, Object>> rows) {
        return Flux.fromStream(rows.stream())
            .map(row -> ProductDTO.builder()
                .productId((Integer) row.get("product_id"))
                .branchId((Integer) row.get("branch_id"))
                .categoryId((Integer) row.get("category_id"))
                .name((String) row.get("name"))
                .code((String) row.get("code"))
                .price((BigDecimal) row.get("price"))
                .buyPrice((BigDecimal) row.get("buy_price"))
                .stock((Integer) row.get("stock"))
                .description((String) row.get("description"))
                .ivaRate((BigDecimal) row.get("iva_rate"))
                .createdAt((LocalDateTime) row.get("created_at"))
                .updatedAt((LocalDateTime) row.get("updated_at"))
                .enabled((String) row.get("enabled"))
                .supplierId((Integer) row.get("supplier_id"))
                .brandId((Integer) row.get("brand_id"))
                .build());
    }

}
