package ru.s1uad_dw.OpenFurnAuthService.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ClientTokensDto {
    List<String> accessTokens;
    List<String> refreshTokens;
}
