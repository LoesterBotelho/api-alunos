package com.botelho.loester.api_alunos.exception;

import java.time.Instant;

public record ErroResponse(
        Integer status, 
        String error,
        String message,        
        Instant timeStamp
) {
}