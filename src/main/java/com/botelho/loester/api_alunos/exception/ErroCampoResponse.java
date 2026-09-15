package com.botelho.loester.api_alunos.exception;

public record ErroCampoResponse(
        String campo,
        String mensagem
) {
}