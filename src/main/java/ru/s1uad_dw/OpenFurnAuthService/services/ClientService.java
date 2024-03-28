package ru.s1uad_dw.OpenFurnAuthService.services;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.InvalidDataException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.ResourceNotFoundException;
import ru.s1uad_dw.OpenFurnAuthService.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private TokenService tokenService;

    public Client findById(UUID clientId){
        return repository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client with specified id not found"));
    }
    public TokensResponseDto save(UUID userId){
        TokensResponseDto tokens = tokenService.genTokenPair(userId, List.of("user"));
        repository.save(new Client(userId, List.of(tokens.getRefreshToken())));
        return tokens;
    }
    public TokensResponseDto addRefreshToken(UUID userId){
        TokensResponseDto tokens = tokenService.genTokenPair(userId, List.of("user"));
        Client client = findById(userId);
        List<String> clientTokens = client.getRefreshTokens();
        clientTokens.add(tokens.getRefreshToken());
        client.setRefreshTokens(clientTokens);
        repository.save(client);
        return tokens;
    }

    public void removeRefreshToken(UUID userId, String refreshToken){
        Client client = findById(userId);
        List<String> clientTokens = client.getRefreshTokens();
        if (clientTokens.contains(refreshToken)){
            clientTokens.remove(refreshToken);
            client.setRefreshTokens(clientTokens);
            repository.save(client);
            return;
        }
        throw new InvalidDataException("Unknown token");
    }

    public TokensResponseDto updateRefreshToken(UUID userId, String refreshToken){
        removeRefreshToken(userId, refreshToken);
        return addRefreshToken(userId);
    }
}
