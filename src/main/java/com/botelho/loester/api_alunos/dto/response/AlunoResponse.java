package com.botelho.loester.api_alunos.dto.response;

import java.time.LocalDate;

public record AlunoResponse(
        Integer id,
        String nome,
        String email,
        LocalDate dataNascimento,
        Double media
) {
}