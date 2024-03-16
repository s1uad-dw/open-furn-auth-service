package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class TokenLifetimeExpiredException extends RuntimeException{
    public TokenLifetimeExpiredException(String message){
        super(message);
    }
}
