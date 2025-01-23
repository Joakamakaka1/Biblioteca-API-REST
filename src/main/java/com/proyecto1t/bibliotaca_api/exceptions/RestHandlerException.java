package com.proyecto1t.bibliotaca_api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestHandlerException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMsg handleNotFoundException(HttpServletRequest req, NotFoundException ex) {
        return new ErrorMsg(
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                HttpStatus.NOT_FOUND,
                req.getRequestURI()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMsg handleBadRequestException(HttpServletRequest req, BadRequestException ex) {
        return new ErrorMsg(
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                HttpStatus.BAD_REQUEST,
                req.getRequestURI()
        );
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMsg handleDuplicateException(HttpServletRequest req, DuplicateException ex) {
        return new ErrorMsg(
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                HttpStatus.CONFLICT,
                req.getRequestURI()
        );
    }

    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMsg handleException(HttpServletRequest req, Exception ex) {
        return new ErrorMsg(
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR,
                req.getRequestURI()
        );
    }

    // Manejo de excepciones de validación con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMsg handleValidationExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> (error instanceof FieldError)
                        ? ((FieldError) error).getField() + "Bad request: " + error.getDefaultMessage()
                        : error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ErrorMsg(
                LocalDateTime.now(),
                errors,
                HttpStatus.BAD_REQUEST,
                req.getRequestURI()
        );
    }
}