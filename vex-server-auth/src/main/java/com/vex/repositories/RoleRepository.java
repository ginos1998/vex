package com.vex.repositories;

import com.vex.enums.RoleType;
import com.vex.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByType(RoleType roleName);
}
