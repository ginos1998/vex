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
@Table("locality")
public class Locality {
    @Id
    @Column("locality_id")
    private Integer localityId;

    @Column("province_id")
    private Integer provinceId;

    @Column("name")
    private String name;
}
