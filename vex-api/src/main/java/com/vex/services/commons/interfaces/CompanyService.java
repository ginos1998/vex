package com.vex.services.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CompanyDTO;
import reactor.core.publisher.Mono;

public interface CompanyService {
      Mono<CompanyDTO> addCompany(CompanyDTO company) throws ServiceException;
      Mono<CompanyDTO> updateCompany(CompanyDTO company, Integer companyId) throws ServiceException;
      Mono<Void> deleteCompanyById(Integer companyId) throws ServiceException;
      Mono<CompanyDTO> getCompanyById(Integer companyId) throws ServiceException;

}
