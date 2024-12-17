package com.proyecto1t.bibliotaca_api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestHandlerException {
    @ExceptionHandler(NotFoundException.class) // Manejo de la excepción NotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND) // Respuesta con estado HTTP 404
    @ResponseBody
    public ErrorMsg handleNotFoundException(HttpServletRequest req, NotFoundException ex) {
        // Devolver un objeto ErrorMsg con los detalles de la excepción
        return new ErrorMsg(ex.getMessage(), req.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class) // Manejo de cualquier otra excepción
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Respuesta con estado HTTP 500
    @ResponseBody
    public ErrorMsg handleException(HttpServletRequest req, Exception ex) {
        return new ErrorMsg(ex.getMessage(), req.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class) // Manejo de la excepción BadRequestException
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Respuesta con estado HTTP 400
    @ResponseBody
    public ErrorMsg handleBadRequestException(HttpServletRequest req, BadRequestException ex) {
        return new ErrorMsg(ex.getMessage(), req.getRequestURI(), LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateException.class) // Manejo de la excepción DuplicateException
    @ResponseStatus(HttpStatus.CONFLICT) // Respuesta con estado HTTP 409
    @ResponseBody
    public ErrorMsg handleDuplicateException(HttpServletRequest req, DuplicateException ex) {
        return new ErrorMsg(ex.getMessage(), req.getRequestURI(), LocalDateTime.now());
    }
}
