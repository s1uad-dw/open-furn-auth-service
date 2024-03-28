package ru.s1uad_dw.OpenFurnAuthService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
