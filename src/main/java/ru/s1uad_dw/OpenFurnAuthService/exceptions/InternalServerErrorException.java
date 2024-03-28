package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
