package com.vex.delegates.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.BranchDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchDelegate {
    Mono<BranchDTO> createBranch(BranchDTO branchDTO, String username) throws ServiceException;
    Mono<BranchDTO> updateBranch(Integer branchId, BranchDTO branchDTO) throws ServiceException;
    Mono<BranchDTO> getBranchById(Integer branchId) throws ServiceException;
    Mono<Void> deleteBranch(Integer branchId) throws ServiceException;
    Flux<BranchDTO> findBranchesByCompanyId(Integer companyId) throws ServiceException;
}
