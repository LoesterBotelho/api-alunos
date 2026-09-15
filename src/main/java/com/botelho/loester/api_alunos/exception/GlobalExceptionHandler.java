package com.botelho.loester.api_alunos.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.botelho.loester.api_alunos.dto.response.ErroResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ============================================================
    // REGISTRO NÃO ENCONTRADO - 404
    // ============================================================

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRegistroNaoEncontrado(
            RegistroNaoEncontradoException ex) {

        ErroResponse erro = new ErroResponse(
                404,
                "Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // ============================================================
    // ERRO DE VALIDAÇÃO - 400
    // ============================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(
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
                400,
                "Bad Request",
                mensagem,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}
