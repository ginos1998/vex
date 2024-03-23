package com.vex.repositories.impl;

import com.vex.models.dtos.LocationDTO;
import com.vex.repositories.ProvinceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProvinceRepositoryImpl extends GenericRepositoryImpl implements ProvinceRepository {
    public ProvinceRepositoryImpl(DatabaseClient databaseClient) {
        super(databaseClient);
    }

    @Override
    public Mono<LocationDTO> getProvinceAndLocalityByLocalityId(Integer localityId) {
        try {
            String query = " SELECT * FROM f_get_province_and_locality_by_locality_id(:locality_id) ";
            return databaseClient.sql(query)
                .bind("locality_id", localityId)
                .fetch()
                .one()
                .flatMap(LocationDTO::fromRows)
                .doOnError(e -> log.error("Error getting province and locality by locality id: {}", localityId, e));
        } catch (Exception e) {
            log.error("Error getting province and locality by locality id: {}", localityId, e);
            return Mono.error(e);
        }
    }
}
