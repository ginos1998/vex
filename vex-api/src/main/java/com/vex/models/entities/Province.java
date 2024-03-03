package com.vex.models.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("provinces")
public class Province {
    @Id
    @Column(value = "province_id")
    private Integer provinceId;

    @Column(value = "name")
    private String name;
}
