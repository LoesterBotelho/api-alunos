package com.botelho.loester.api_alunos.exception;

public class EmailJaCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}