package com.botelho.loester.api_alunos.exception;

import java.time.Instant;
import java.util.List;

public record ErroAtributoResponse(
		int status,
		String mensagem,
		Instant timestamp,
		List<ErroAtributo> errosAtributos
) {}
