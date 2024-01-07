package com.vex.services;

import com.vex.enums.RoleType;
import com.vex.models.Role;
import com.vex.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public Set<Role> findRolesByRoleType(List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        roles.forEach(r -> {
            Role role = roleRepository.findByType(RoleType.valueOf(r))
                .orElseThrow(()-> new RuntimeException("role not found"));
            roleSet.add(role);
        });
        return roleSet;
    }
}
