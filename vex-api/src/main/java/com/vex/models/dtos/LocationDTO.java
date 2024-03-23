package com.vex.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LocationDTO {
    private String localityName;
    private String provinceName;
    private String address;

    public static Mono<LocationDTO> fromRows(Map<String, Object> rows) {
        if (rows.isEmpty()) {
            return Mono.empty();
        }

        return Mono.just(LocationDTO.builder()
                .localityName((String) rows.get("locality_name"))
                .provinceName((String) rows.get("province_name"))
                .build()
        );
    }
}
