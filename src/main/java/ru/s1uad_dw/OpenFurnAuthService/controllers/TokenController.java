package ru.s1uad_dw.OpenFurnAuthService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.TokenLifetimeExpiredException;
import ru.s1uad_dw.OpenFurnAuthService.services.TokenService;

@RestController
@RequestMapping("api/v1/auth_service")
@AllArgsConstructor
public class TokenController {
    @Autowired
    private TokenService service;

    @PostMapping("/update_tokens")
    @Operation(summary = "Update access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New access and refresh tokens",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token lifetime has expired",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    public TokensResponseDto updatingTokens(@RequestBody String token) {
        return service.updateTokens(token);
    }
}
