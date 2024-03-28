package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class ServerNotResponseException extends RuntimeException{
    public ServerNotResponseException(String message) {
        super(message);
    }
}
