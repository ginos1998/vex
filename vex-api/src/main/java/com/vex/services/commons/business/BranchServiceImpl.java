package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.BranchDTO;
import com.vex.models.entities.Branch;
import com.vex.models.entities.Personal;
import com.vex.models.entities.PersonalBranch;
import com.vex.repositories.r2dbc.BranchR2dbcRepository;
import com.vex.repositories.r2dbc.PersonalBranchR2dbcRepository;
import com.vex.services.commons.interfaces.BranchService;
import com.vex.utils.Constants;
import com.vex.utils.DefaultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {
    private final BranchR2dbcRepository branchR2dbcRepository;
    private final PersonalBranchR2dbcRepository personalBranchR2dbcRepository;

    @Override
    public Mono<BranchDTO> createBranch(BranchDTO branchDTO, Personal personal) throws ServiceException {
        try {
            if (isInvalidInput(branchDTO)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT,branchDTO.toString());
            }
            Branch newBranch = DefaultMapper.getInstance().getMapper().map(branchDTO, Branch.class);
            return branchR2dbcRepository.save(newBranch)
                .flatMap(b -> {
                    log.info("Branch saved: {}", b.toString());
                    BranchDTO createdBranch = DefaultMapper.getInstance().getMapper().map(b, BranchDTO.class);
                    return Mono.just(createdBranch);
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(branchCreated -> {
                    PersonalBranch personalBranch = PersonalBranch.builder()
                        .branchId(branchCreated.getBranchId())
                        .personalId(personal.getPersonalId())
                        .active(Constants.CHAR_Y)
                        .admin(Constants.CHAR_Y)
                        .build();
                    personalBranchR2dbcRepository.save(personalBranch)
                        .subscribe(pb -> log.info("PersonalBranch saved: {}", pb.toString()));
                })
                ;
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_BRANCH, branchDTO.toString());
        }
    }

    @Override
    public Mono<BranchDTO> updateBranch(Integer branchId, BranchDTO branchDTO) throws ServiceException {
        try {
            return branchR2dbcRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.BRANCH_NOT_FOUND, branchId)))
                .single()
                .flatMap(branch -> {
                    log.info("Updating branch: {}", branch.toString());
                    DefaultMapper.getInstance().mapNonNull(branchDTO, branch);
                    return branchR2dbcRepository.save(branch)
                        .flatMap(updatedBranch -> {
                            log.info("Branch updated: {}", updatedBranch.toString());
                            BranchDTO updatedBranchDTO = DefaultMapper.getInstance().map(updatedBranch, BranchDTO.class);
                            return Mono.just(updatedBranchDTO);
                        });
                });
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_BRANCH, branchDTO.toString());
        }
    }

    @Override
    public Mono<BranchDTO> getBranchById(Integer branchId) throws ServiceException {
        try {
            return branchR2dbcRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.BRANCH_NOT_FOUND, branchId)))
                .single()
                .flatMap(b -> {
                    log.info("Branch found: {}", b.toString());
                    BranchDTO branchDTO = DefaultMapper.getInstance().getMapper().map(b, BranchDTO.class);
                    return Mono.just(branchDTO);
                });
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_BRANCH, branchId);
        }
    }

    @Override
    public Mono<Void> deleteBranch(Integer branchId) throws ServiceException {
        try {
            return branchR2dbcRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.BRANCH_NOT_FOUND, branchId)))
                .single()
                .flatMap(b -> {
                    log.info("Deleting branch: {}", b.toString());
                    return branchR2dbcRepository.deleteById(branchId)
                        .then(Mono.defer(() -> {
                            log.info("Branch deleted: {}", branchId);
                            return Mono.empty();
                        }));
                });
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_DELETING_BRANCH, branchId);
        }
    }

    @Override
    public Flux<BranchDTO> findBranchesByCompanyId(Integer companyId) throws ServiceException {
        try {
            return branchR2dbcRepository.findAllByCompanyIdAndActiveEqualsIgnoreCase(companyId, Constants.CHAR_Y)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.NO_BRANCHES_FOUND, companyId)))
                .map(b -> DefaultMapper.getInstance().getMapper().map(b, BranchDTO.class));
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_BRANCHES_BY_COMPANY, companyId);
        }
    }

    private boolean isInvalidInput(BranchDTO branchDTO) {
        if(branchDTO.getInitActivity() == null) branchDTO.setInitActivity(LocalDateTime.now());
        if(branchDTO.getActive() == null) branchDTO.setActive(Constants.CHAR_Y);
        if(!branchDTO.getActive().equalsIgnoreCase(Constants.CHAR_N)) branchDTO.setActive(Constants.CHAR_Y);

        return branchDTO.getLocalityId() == null
            || branchDTO.getName() == null || branchDTO.getCompanyId() == null;
    }
}
