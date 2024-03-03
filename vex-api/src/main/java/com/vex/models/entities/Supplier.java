package com.vex.models.entities;

import com.vex.utils.Constants;
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
@Table("supplier")
public class Supplier {
    @Id
    @Column("supplier_id")
    private Integer supplierId;

    @Column("name")
    private String name;

    @Column("cuit_cuil")
    private String cuitCuil;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("locality_id")
    private Integer localityId;

    @Column("branch_id")
    private Integer branchId;

    @Column("active")
    private String active;

    public boolean isActive() {
        return active.equals(Constants.CHAR_Y);
    }
}
