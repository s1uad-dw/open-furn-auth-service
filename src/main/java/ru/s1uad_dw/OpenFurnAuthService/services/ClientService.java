package ru.s1uad_dw.OpenFurnAuthService.services;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;

import java.util.UUID;

@Service
public class ClientService {
    public UUID save(Client client){
        return client.getId(); //todo сохранение в бд через ClientRepository
    }
}
