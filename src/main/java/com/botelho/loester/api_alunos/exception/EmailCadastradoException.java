package com.botelho.loester.api_alunos.exception;

public class EmailCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailCadastradoException(String message) {
        super(message);
    }
}