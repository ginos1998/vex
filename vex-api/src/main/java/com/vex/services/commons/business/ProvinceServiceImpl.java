package com.vex.services.commons.business;

import com.vex.models.dtos.LocationDTO;
import com.vex.repositories.ProvinceRepository;
import com.vex.services.commons.interfaces.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Override
    public Mono<LocationDTO> getProvinceAndLocalityByLocalityId(Integer localityId) {
        return provinceRepository.getProvinceAndLocalityByLocalityId(localityId);
    }
}
