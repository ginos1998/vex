package com.vex.controller;

import com.vex.models.dtos.CreateClientDto;
import com.vex.models.dtos.MessageDto;
import com.vex.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@RequestBody CreateClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<MessageDto> update(@PathVariable Integer id, @RequestBody CreateClientDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(id, dto));
    }
}
