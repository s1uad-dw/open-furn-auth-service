package ru.s1uad_dw.OpenFurnAuthService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientAuthDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientRegDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.services.AuthService;
import ru.s1uad_dw.OpenFurnAuthService.services.TokenService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth_service")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("auth")
    @Operation(summary = "Authorization by email|username|phone and password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access and refresh tokens",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid data",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    public TokensResponseDto auth(@RequestBody ClientAuthDataDto clientData){
        return authService.auth(clientData);
    }
    @PostMapping("reg")
    @Operation(summary = "Registration by email|username|phone and password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access and refresh tokens",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid data",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already registered",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    public TokensResponseDto reg(@RequestBody ClientRegDataDto clientData){
        return authService.reg(clientData);
    }

    @PostMapping("logout")
    @Operation(summary = "Authorization by email|username|phone and password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful logout",
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
    public String logout(@RequestHeader("Authorization") String token){
        return authService.logout(token);
    }

    @PostMapping("update_tokens")
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
                    description = "Token expired",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    public TokensResponseDto updateTokens(@RequestHeader("Authorization") String token) {
        return authService.updateTokens(token);
    }

}
