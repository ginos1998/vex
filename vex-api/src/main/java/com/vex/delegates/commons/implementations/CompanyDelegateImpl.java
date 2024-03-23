package com.vex.delegates.commons.implementations;

import com.vex.delegates.commons.interfaces.CompanyDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CompanyDTO;
import com.vex.services.commons.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CompanyDelegateImpl implements CompanyDelegate {

    private final CompanyService companyService;

    @Override
    public Mono<CompanyDTO> addCompany(CompanyDTO company) throws ServiceException {
        return companyService.addCompany(company);
    }

    @Override
    public Mono<CompanyDTO> updateCompany(CompanyDTO company, Integer companyId) throws ServiceException {
        return companyService.updateCompany(company, companyId);
    }

    @Override
    public Mono<Void> deleteCompanyById(Integer companyId) throws ServiceException {
        return companyService.deleteCompanyById(companyId);
    }

    @Override
    public Mono<CompanyDTO> getCompanyById(Integer companyId) throws ServiceException {
        return companyService.getCompanyById(companyId);
    }
}
