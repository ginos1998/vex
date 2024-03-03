package com.vex.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("brand")
public class Brand {
    @Id
    @Column(value = "brand_id")
    private Integer brandId;
    @Column(value = "name")
    private String name;
    @Column(value = "active")
    private String active;
}
