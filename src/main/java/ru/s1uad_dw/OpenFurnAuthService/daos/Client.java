package ru.s1uad_dw.OpenFurnAuthService.daos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clients")
public class Client {
    @Id
    private UUID id;
    private List<String> refreshTokens;
}
