package com.vex.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("personal")
public class Personal {
    @Id
    @Column(value = "personal_id")
    private Integer personalId;
    @Column(value = "username")
    private String username;
    @Column(value = "created_at")
    private Date createdAt;
    @Column(value = "updated_at")
    private Date updatedAt;
    @Column(value = "enabled")
    private Boolean enabled;
    @Column(value = "address")
    private String address;
    @Column(value = "phone")
    private String phone;
    @Column(value = "doc_type_id")
    private Integer docTypeId;
    @Column(value = "doc_number")
    private String docNumber;

}

