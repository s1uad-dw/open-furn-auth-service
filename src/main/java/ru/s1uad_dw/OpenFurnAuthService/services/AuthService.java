package ru.s1uad_dw.OpenFurnAuthService.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientAuthDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientRegDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.InvalidDataException;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private RequestService requestService;

    public TokensResponseDto reg(ClientRegDataDto clientData) {
        CloseableHttpResponse response = requestService.request(
                "http://localhost:8080/api/v1/user_service", requestService.objectToJsonBody(clientData));

        requestService.checkStatusCode(response.getStatusLine().getStatusCode(), response);

        return clientService.save(requestService.getUserIdFromResponse(response));
    }

    public TokensResponseDto auth(ClientAuthDataDto clientData) {
        CloseableHttpResponse response = requestService.request(
                "http://localhost:8080/api/v1/user_service/login", requestService.objectToJsonBody(clientData));

        requestService.checkStatusCode(response.getStatusLine().getStatusCode(), response);

        return clientService.addRefreshToken(requestService.getUserIdFromResponse(response));
    }

    public String logout(String token) {
        tokenService.checkTokenExpiration(token);

        clientService.removeRefreshToken(tokenService.getSubject(token), token);
        return "Success";
    }

    public TokensResponseDto updateTokens(String token) {
        tokenService.checkTokenExpiration(token);

        return clientService.updateRefreshToken(tokenService.getSubject(token), token);
    }
}
