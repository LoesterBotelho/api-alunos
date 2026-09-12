package com.botelho.loester.api_alunos.dto.response;

import java.time.LocalDateTime;

public record ErroResponse(
        Integer status,
        String erro,
        String mensagem,
        LocalDateTime dataHora
) {
}