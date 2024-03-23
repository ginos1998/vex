package com.vex.repositories;

import com.vex.models.dtos.LocationDTO;
import reactor.core.publisher.Mono;

public interface ProvinceRepository {
    Mono<LocationDTO> getProvinceAndLocalityByLocalityId(Integer localityId);
}
