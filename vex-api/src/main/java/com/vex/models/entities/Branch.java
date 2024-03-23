package com.vex.models.entities;

import com.vex.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("branch")
public class Branch {
    @Id
    @Column("branch_id")
    private Integer branchId;

    @Column("company_id")
    private Integer companyId;

    @Column("locality_id")
    private Integer localityId;

    @Column("name")
    private String name;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("cuit")
    private String cuit;

    @Column("active")
    private String active;

    @Column("init_activity")
    private LocalDateTime initActivity;

    public boolean isActive() {
        return active.equals(Constants.CHAR_Y);
    }
}
