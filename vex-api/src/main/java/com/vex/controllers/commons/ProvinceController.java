package com.vex.controllers.commons;

import com.vex.models.dtos.LocationDTO;
import com.vex.services.commons.interfaces.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping("/locality/{localityId}")
    public Mono<LocationDTO> getProvinceAndLocalityByLocalityId(@PathVariable Integer localityId) {
        return provinceService.getProvinceAndLocalityByLocalityId(localityId);
    }
}
