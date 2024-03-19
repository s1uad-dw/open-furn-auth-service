package ru.s1uad_dw.OpenFurnAuthService.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.reporitories.ClientRepository;

import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    public UUID save(Client client){
        return client.getId(); //todo сохранение в бд через ClientRepository
    }
}
