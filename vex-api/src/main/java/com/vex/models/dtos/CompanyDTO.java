package com.vex.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.vex.utils.Views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO {
    @JsonView(Views.Public.class)
    private Integer companyId;
    private Integer localityId;
    @JsonView(Views.Public.class)
    private LocationDTO location;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String registeredName;
    @JsonView(Views.Public.class)
    private String cuit;
    private String address;
    @JsonView(Views.Public.class)
    private String phone;
    @JsonView(Views.Public.class)
    private String email;
    private String active;
    @JsonView(Views.Public.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime initActivity;
}
