package ru.s1uad_dw.OpenFurnAuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.UserIsNotRegisteredException;
import ru.s1uad_dw.OpenFurnAuthService.reporitories.ClientRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public void save(Client client) {
        repository.save(client);
    }

    public void removeRefreshTokenFromList(UUID id, String token) {
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            client.get().getRefreshTokens().removeIf(t -> t.equals(token));
            repository.save(client.get());
        } else {
            throw new UserIsNotRegisteredException("User not registered");
        }
    }
}
