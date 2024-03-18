package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class UserIsNotRegisteredException extends RuntimeException{
    public UserIsNotRegisteredException(String message){
        super(message);
    }
}
