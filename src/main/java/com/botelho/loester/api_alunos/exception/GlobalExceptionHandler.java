package com.botelho.loester.api_alunos.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.botelho.loester.api_alunos.dto.response.ErroResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> tratarRegistroNaoEncontrado(RegistroNaoEncontradoException ex) {

		ErroResponse erro = new ErroResponse(
				404, 
				"Not Found", 
				ex.getMessage(), 
				LocalDateTime.now()
				);

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(erro);
	}

}