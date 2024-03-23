package com.vex.services.commons.interfaces;

import com.vex.models.dtos.LocationDTO;
import reactor.core.publisher.Mono;

public interface ProvinceService {
    Mono<LocationDTO> getProvinceAndLocalityByLocalityId(Integer localityId);
}
