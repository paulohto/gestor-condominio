package com.gestorcondominio.mssindico.exception.controller;

import com.gestorcondominio.mssindico.exception.service.ControllerNotFoundException;
import com.gestorcondominio.mssindico.exception.service.DataBaseException;
import com.gestorcondominio.mssindico.exception.service.DefaultError;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    private DefaultError error = new DefaultError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<DefaultError> entityNotFound(DataBaseException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Database error");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    //ADICIONADO
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(EntityNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoForm> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidacaoForm validacaoForm = new ValidacaoForm();
        validacaoForm.setTimestamp(Instant.now());
        validacaoForm.setStatus(status.value());
        validacaoForm.setError("Erro de validação");
        validacaoForm.setMessage(exception.getMessage());
        validacaoForm.setPath(request.getRequestURI());

        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            validacaoForm.addMenssagens(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(validacaoForm);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<DefaultError> handleFeignException(FeignException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.resolve(exception.status());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        DefaultError error = new DefaultError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Erro de comunicação com serviço externo");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
