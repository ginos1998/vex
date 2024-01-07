package com.vex.services;

import com.vex.models.Role;
import com.vex.models.User;
import com.vex.models.dtos.CreateAppUserDto;
import com.vex.models.dtos.MessageDto;
import com.vex.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto createUser(CreateAppUserDto dto){
        User appUser = User.builder()
            .username(dto.username())
            .password(passwordEncoder.encode(dto.password()))
            .enabled(true)
            .locked(false)
            .credentialsExpired(false)
            .expired(false)
            .createdAt(new Date())
            .build();

        Set<Role> roles = roleService.findRolesByRoleType(dto.roles());
        appUser.setRoles(roles);
        userRepository.save(appUser);
        log.info("user {} saved successfully!", appUser.getUsername());
        return new MessageDto("user " + appUser.getUsername() + " saved successfully!");
    }

}
