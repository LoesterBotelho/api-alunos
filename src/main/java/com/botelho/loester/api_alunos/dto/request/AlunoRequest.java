package com.botelho.loester.api_alunos.dto.request;

import java.time.LocalDate;

public record AlunoRequest(
        String nome,
        String email,
        String senha,
        LocalDate dataNascimento,
        Double media
) {
}

