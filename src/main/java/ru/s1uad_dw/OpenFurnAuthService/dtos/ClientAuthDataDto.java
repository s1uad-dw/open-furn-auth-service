package ru.s1uad_dw.OpenFurnAuthService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientAuthDataDto {
    private String login;
    private String pass;
}
