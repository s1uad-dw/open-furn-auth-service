package ru.s1uad_dw.OpenFurnAuthService.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.s1uad_dw.OpenFurnAuthService.dtos.AppError;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.InvalidDataException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.ResourceNotFoundException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.TokenLifetimeExpiredException;
import ru.s1uad_dw.OpenFurnAuthService.exceptions.UserAlreadyRegisteredException;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(new AppError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURL().toString()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchInvalidDataException(InvalidDataException e, HttpServletRequest request) {
        return new ResponseEntity<>(new AppError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                request.getRequestURL().toString()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUserAlreadyRegisteredException(UserAlreadyRegisteredException e, HttpServletRequest request) {
        return new ResponseEntity<>(new AppError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURL().toString()
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchTokenLifetimeExpiredException(TokenLifetimeExpiredException e, HttpServletRequest request) {
        return new ResponseEntity<>(new AppError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURL().toString()
        ), HttpStatus.CONFLICT);
    }
}
