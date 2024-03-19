package ru.s1uad_dw.OpenFurnAuthService.reporitories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    public List<String> findRefreshTokensById(UUID id);
    public void deleteByRefreshTokensContaining(String token);
}