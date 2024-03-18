package ru.s1uad_dw.OpenFurnAuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.s1uad_dw.OpenFurnAuthService.daos.Client;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientAuthDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.ClientRegDataDto;
import ru.s1uad_dw.OpenFurnAuthService.dtos.TokensResponseDto;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.InvalidDataException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.UserAlreadyRegisteredException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.UserIsNotRegisteredException;
import ru.s1uad_dw.OpenFurnAuthService.validators.ClientDataValidator;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ClientService clientService;

    public TokensResponseDto reg(ClientRegDataDto clientData) {
        checkClientData(clientData);
        if (!isUserRegistered(clientData.getLogin())) {
            UUID userId = saveUserToUserService(clientData);
            TokensResponseDto tokenPair = tokenService.genTokenPair(userId, List.of("user"));
            clientService.save(new Client(userId, tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
            return tokenPair;
        } else {
            throw new UserAlreadyRegisteredException("User already registered");
        }
    }

    public TokensResponseDto auth(ClientAuthDataDto clientData) {
        if (isUserRegistered(clientData.getLogin())) {
            UUID userId = getUserIdFromUserService(clientData.getLogin());
            TokensResponseDto tokenPair = tokenService.genTokenPair(userId, List.of("user"));
            clientService.save(new Client(userId, tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
            return tokenPair;
        } else {
            throw new UserIsNotRegisteredException("User is not registered");
        }
    }


    public boolean isUserRegistered(String login) {
        return true;  //todo запрос в сервис пользователей
    }

    public UUID getUserIdFromUserService(String login) {
        return UUID.randomUUID(); //todo запрос в сервис пользователей
    }

    public UUID saveUserToUserService(ClientRegDataDto clientData) {
        return UUID.randomUUID(); //todo запрос в сервис пользователей
    }

    public void logout(String token) {
        if (tokenService.isTokenValid(token)) {
            //todo тут удаляем refresh токен из бд через репозиторий
        }
    }

    public void checkClientData(ClientRegDataDto clientData) {
        switch (clientData.getLoginType()) {
            case "email":
                if (!ClientDataValidator.isValidEmail(clientData.getLogin()))
                    throw new InvalidDataException("Incorrect email");
                break;
            case "phone":
                if (!ClientDataValidator.isValidPhone(clientData.getLogin()))
                    throw new InvalidDataException("Incorrect phone");
                break;
        }
        if (!ClientDataValidator.isValidUsername(clientData.getUsername()))
            throw new InvalidDataException("Incorrect username");
    }
}
