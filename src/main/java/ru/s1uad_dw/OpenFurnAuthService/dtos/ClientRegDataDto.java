package ru.s1uad_dw.OpenFurnAuthService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientRegDataDto {
    private String login;
    private String username;
    private String pass;
    @Schema(description = "login type", examples = {
            "email",
            "phone",
            "username"
    })
    private String loginType; //
}
