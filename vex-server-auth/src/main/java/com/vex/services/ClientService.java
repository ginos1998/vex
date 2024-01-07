package com.vex.services;

import com.vex.models.Client;
import com.vex.models.dtos.CreateClientDto;
import com.vex.models.dtos.MessageDto;
import com.vex.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisteredClient registeredClient) {
        // not update
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(()-> new RuntimeException("client not found with id: " + id));
        return Client.toRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(()-> new RuntimeException("client not found with clientId: " + clientId));
        return Client.toRegisteredClient(client);
    }

    public MessageDto create(CreateClientDto dto){
        Client client = clientFromDto(dto);
        clientRepository.save(client);
        return new MessageDto("client " + client.getClientId() + " saved");
    }

    public MessageDto update(Integer id, CreateClientDto dto){
        Client clientDB = clientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("client not found"));
        Client client = clientFromDto(dto);
        client.setClientId(clientDB.getClientId());
        clientRepository.save(client);
        return new MessageDto("client " + client.getClientId() + " updated successfully");
    }

    // private methods
    private Client clientFromDto(CreateClientDto dto){
        return Client.builder()
                .clientId(dto.getClientId())
                .clientSecret(passwordEncoder.encode(dto.getClientSecret()))
                .authenticationMethods(dto.getAuthenticationMethods())
                .authorizationGrantTypes(dto.getAuthorizationGrantTypes())
                .redirectUris(dto.getRedirectUris())
                .scopes(dto.getScopes())
                .requireProofKey(dto.isRequireProofKey())
                .build();
    }

}
