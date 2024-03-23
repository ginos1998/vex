package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CompanyDTO;
import com.vex.models.entities.Company;
import com.vex.repositories.ProvinceRepository;
import com.vex.repositories.r2dbc.CompanyR2dbcRepository;
import com.vex.services.commons.interfaces.CompanyService;
import com.vex.utils.Constants;
import com.vex.utils.DefaultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyR2dbcRepository companyR2dbcRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public Mono<CompanyDTO> addCompany(CompanyDTO company) throws ServiceException {
        try {
            if (isInvalidInput(company)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, company.toString());
            }
            Company newCompany = DefaultMapper.getInstance().getMapper().map(company, Company.class);
            return companyR2dbcRepository.save(newCompany)
                .flatMap(c -> {
                    log.info("Company saved: {}", c.toString());
                    return Mono.just(DefaultMapper.getInstance().getMapper().map(c, CompanyDTO.class));
                });
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage(), company.toString());
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_COMPANY, errors);
        }
    }

    @Override
    public Mono<CompanyDTO> updateCompany(CompanyDTO companyDTO, Integer companyId) throws ServiceException {
        try {
            if (isInvalidInput(companyDTO)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, companyDTO.toString());
            }
            return companyR2dbcRepository.findById(companyId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.COMPANY_NOT_FOUND, companyId)))
                .single()
                .flatMap(company ->{
                    log.info("Updating company: {}", company.toString());
                    DefaultMapper.getInstance().mapNonNull(companyDTO, company);
                    return companyR2dbcRepository.save(company)
                        .flatMap(updatedCompany -> {
                            log.info("Company updated: {}", updatedCompany.toString());
                            return Mono.just(DefaultMapper.getInstance().getMapper().map(updatedCompany, CompanyDTO.class));
                        });
                });
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage(), companyDTO.toString());
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_COMPANY, errors);
        }
    }

    @Override
    public Mono<Void> deleteCompanyById(Integer companyId) throws ServiceException {
        try {
            return companyR2dbcRepository.findById(companyId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.COMPANY_NOT_FOUND, companyId)))
                .single()
                .flatMap(c -> {
                    log.info("Deleting company: {}", c.toString());
                    return companyR2dbcRepository.deleteById(companyId)
                        .then(Mono.defer(() -> {
                            log.info("Company deleted: {}", companyId);
                            return Mono.empty();
                        }));
                });
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage(), "Company Id: " + companyId);
            throw new ServiceException(e, ExceptionType.ERROR_DELETING_COMPANY, errors);
        }
    }

    @Override
    public Mono<CompanyDTO> getCompanyById(Integer companyId) throws ServiceException {
        try {
            return companyR2dbcRepository.findCompanyByCompanyIdAndActiveEqualsIgnoreCase(companyId, Constants.CHAR_Y)
                .flatMap(c -> {
                    log.info("Company found: {}", c.toString());
                    return Mono.just(DefaultMapper.getInstance().getMapper().map(c, CompanyDTO.class))
                        .flatMap(companyDTO -> provinceRepository.getProvinceAndLocalityByLocalityId(companyDTO.getLocalityId())
                            .flatMap(locationDTO -> {
                                locationDTO.setAddress(companyDTO.getAddress());
                                companyDTO.setLocation(locationDTO);
                                return Mono.just(companyDTO);
                            }));
                });
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage(), "Company Id: " + companyId);
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_COMPANY, errors);
        }
    }

    private boolean isInvalidInput(CompanyDTO company) {
        if (company == null) return true;
        if (company.getActive() == null) company.setActive(Constants.CHAR_Y);
        if (!company.getActive().equalsIgnoreCase(Constants.CHAR_N)) company.setActive(Constants.CHAR_Y);
        if (company.getInitActivity() == null) company.setInitActivity(LocalDateTime.now());

        return company.getName() == null || company.getName().isEmpty()
                || company.getRegisteredName() == null || company.getRegisteredName().isEmpty();
    }

}
