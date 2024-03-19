package ru.s1uad_dw.OpenFurnAuthService.daos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name="Clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private List<String> accessTokens;
    private List<String> refreshTokens;
    public Client(UUID id, String accessToken, String refreshToken){
        new Client(
                id,
                List.of(accessToken),
                List.of(refreshToken)
        );
    }
}
