package ru.s1uad_dw.OpenFurnAuthService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientRegDataDto {
    private String username;
    private String email;
    private String phone;
    private String password;
}
