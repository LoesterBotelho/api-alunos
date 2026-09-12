package com.botelho.loester.api_alunos.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.botelho.loester.api_alunos.model.Aluno;

public record AlunoResponse(
        Integer id,
        String nome,
        String email,
        LocalDate dataNascimento,
        Double media
) {

    public static AlunoResponse from(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getDataNascimento(),
                aluno.getMedia()
        );
    }

    public static List<AlunoResponse> from(List<Aluno> alunos) {
        return alunos.stream()
                .map(AlunoResponse::from)
                .toList();
    }
}