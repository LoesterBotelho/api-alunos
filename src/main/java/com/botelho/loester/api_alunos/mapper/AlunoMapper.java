package com.botelho.loester.api_alunos.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.model.Aluno;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoResponse toResponse(Aluno aluno);

    List<AlunoResponse> toResponseList(List<Aluno> alunos);
}