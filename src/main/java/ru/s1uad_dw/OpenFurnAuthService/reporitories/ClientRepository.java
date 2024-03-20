package ru.s1uad_dw.OpenFurnAuthService.reporitories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    public Optional<Client> findById(UUID id);
    public List<String> findRefreshTokensById(UUID id);
    public void deleteRefreshTokenById(ClientTokenDeleteDto client);
}