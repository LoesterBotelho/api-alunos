package com.botelho.loester.api_alunos.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ------------------------------------------------------------
    // REGISTRO NÃO ENCONTRADO - 404
    // ------------------------------------------------------------

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleRegistroNaoEncontradoException(
            RegistroNaoEncontradoException ex) {

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // ------------------------------------------------------------
    // E-MAIL JÁ CADASTRADO - 409
    // ------------------------------------------------------------

    @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity<ErroResponse> handleEmailCadastradoException(
            EmailCadastradoException ex) {

        ErroResponse erro = new ErroResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    // ------------------------------------------------------------
    // ERRO DE VALIDAÇÃO - 400
    // ------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        String mensagem = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField()
                        + ": "
                        + erro.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        ErroResponse erro = new ErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensagem,
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}
