package com.botelho.loester.api_alunos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	private final AlunoService alunoService;

	@Autowired
	public AlunoController(AlunoService alunoService) {
		this.alunoService = alunoService;
	}
	
	@GetMapping
	public List<AlunoResponse> listarTodos() {
		return alunoService.listarTodos();
	}	
	
	@GetMapping("/{id}")
	public AlunoResponse listar(@PathVariable Integer id) {
		return alunoService.listar(id);
	}		
	
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse incluir(@RequestBody AlunoRequest request) {
        return alunoService.incluir(request);
    }

    @PutMapping("/{id}")
    public AlunoResponse atualizar(
            @PathVariable Integer id,
            @RequestBody AlunoRequest request) {

        return alunoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        alunoService.deletar(id);
    }

    @PatchMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(
            @PathVariable Integer id,
            @RequestBody AlteraSenhaRequest request) {

        alunoService.alterarSenha(id, request);
    }

    
}
