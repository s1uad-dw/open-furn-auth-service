package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class UnknownTokenException extends RuntimeException{
    public UnknownTokenException(String message){
        super(message);
    }
}
