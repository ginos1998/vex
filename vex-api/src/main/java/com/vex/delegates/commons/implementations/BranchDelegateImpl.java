package com.vex.delegates.commons.implementations;

import com.vex.delegates.commons.interfaces.BranchDelegate;
import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.BranchDTO;
import com.vex.services.commons.interfaces.BranchService;
import com.vex.services.commons.interfaces.CompanyService;
import com.vex.services.commons.interfaces.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchDelegateImpl implements BranchDelegate {
    private final BranchService branchService;
    private final CompanyService companyService;
    private final PersonalService personalService;

    @Override
    public Mono<BranchDTO> createBranch(BranchDTO branchDTO, String username) throws ServiceException {
        return personalService.findByUsernameAndEnabled(username, true)
            .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.PERSONAL_NOT_FOUND, username)))
            .flatMap(personal -> branchService.createBranch(branchDTO, personal));
    }

    @Override
    public Mono<BranchDTO> updateBranch(Integer branchId, BranchDTO branchDTO) throws ServiceException {
        return branchService.updateBranch(branchId, branchDTO);
    }

    @Override
    public Mono<BranchDTO> getBranchById(Integer branchId) throws ServiceException {
        return branchService.getBranchById(branchId);
    }

    @Override
    public Mono<Void> deleteBranch(Integer branchId) throws ServiceException {
        return branchService.deleteBranch(branchId);
    }

    @Override
    public Flux<BranchDTO> findBranchesByCompanyId(Integer companyId) throws ServiceException {
        return companyService.getCompanyById(companyId)
            .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.COMPANY_NOT_FOUND, companyId)))
            .flatMapMany(company -> branchService.findBranchesByCompanyId(companyId));
    }

}
