package com.vex.controller;

import com.vex.models.dtos.CreateAppUserDto;
import com.vex.models.dtos.MessageDto;
import com.vex.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> createUser(@RequestBody CreateAppUserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(dto));
    }
}
