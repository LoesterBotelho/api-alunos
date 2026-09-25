package com.botelho.loester.api_alunos.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    // ------------------------------------------------------------
    // REGISTRO NÃO ENCONTRADO - 404
    // ------------------------------------------------------------

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    ResponseEntity<ErroResponse> handleRegistroNaoEncontradoException(
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
    ResponseEntity<ErroResponse> handleEmailCadastradoException(
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
    ResponseEntity<ErroAtributoResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<ErroAtributo> erros = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> new ErroAtributo(
                        erro.getField(),
                        erro.getDefaultMessage()
                ))
                .toList();

        ErroAtributoResponse erro = new ErroAtributoResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                Instant.now(),
                erros
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}