package ru.s1uad_dw.OpenFurnAuthService.exceptions;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException (String message){
        super(message);
    }
}
