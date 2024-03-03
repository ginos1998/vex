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
@Table("personal_branch")
public class PersonalBranch {
    @Id
    @Column("personal_branch_id")
    private Integer personalBranchId;

    @Column("personal_id")
    private Integer personalId;

    @Column("branch_id")
    private Integer branchId;

    @Column("active")
    private String active;

    @Column("admin")
    private String admin;

    public boolean isAdmin() {
        return admin.equals(Constants.CHAR_Y);
    }

    public boolean isActive() {
        return active.equals(Constants.CHAR_Y);
    }
}
