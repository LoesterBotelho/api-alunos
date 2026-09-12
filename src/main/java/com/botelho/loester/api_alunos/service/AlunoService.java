package com.botelho.loester.api_alunos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;
import com.botelho.loester.api_alunos.model.Aluno;
import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;


@Service
public class AlunoService {

	private final List<Aluno> lista = new ArrayList<Aluno>();

	public AlunoService() {

		Aluno aluno1 = new Aluno(1, 
				"Loester Botelho", 
				"loester.botelho@empresa.com",
				"P@ssW0rd#2026!",
				LocalDate.of(1990, 9, 11),
				10.0);

		Aluno aluno2 = new Aluno(2, 
				"Henrique", 
				"henrique.developer@empresa.com",
				"1234567",
				LocalDate.of(2000, 9, 11),
				8.75);
		
		Aluno aluno3 = new Aluno(3, 
				"Heloisa", 
				"heloisa.developer@empresa.com",
				"1234",
				LocalDate.of(1970, 9, 11),
				8.75);		

		lista.add(aluno1);
		lista.add(aluno2);
		lista.add(aluno3);		
	}

	public List<AlunoResponse> listarTodos() {
		return AlunoResponse.from(lista);
	}

	public AlunoResponse listar(Integer id) {
		
//		for (Aluno aluno : lista) {
//
//		    if (aluno.getId().equals(id)) {
//		        return AlunoResponse.from(aluno);
//		    }
//		}
//		
//		throw new RegistroNaoEncontradoException("Aluno não encontrado. Id: " + id);	
		
		return lista.stream()
				.filter(aluno -> aluno.getId().equals(id)) 
				.findFirst() .map(AlunoResponse::from) 
				.orElseThrow(() -> new RegistroNaoEncontradoException("Aluno não encontrado. Id: " + id ) );	
				
	}
	
	
    public AlunoResponse incluir(AlunoRequest request) {

        Integer novoId = lista.stream()
                .mapToInt(Aluno::getId)
                .max()
                .orElse(0) + 1;

        Aluno aluno = new Aluno(
                novoId,
                request.nome(),
                request.email(),
                request.senha(),
                request.dataNascimento(),
                request.media()
        );

        lista.add(aluno);

        return AlunoResponse.from(aluno);
    }	
	
    private Aluno buscarAluno(Integer id) {

        return lista.stream()
                .filter(aluno -> aluno.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Aluno não encontrado. Id: " + id
                        )
                );
    }
    
    public void deletar(Integer id) {

        Aluno aluno = buscarAluno(id);

        lista.remove(aluno);
    }
    
    public void alterarSenha(Integer id, AlteraSenhaRequest request) {

        Aluno aluno = buscarAluno(id);

        aluno.setSenha(request.novaSenha());
    }
    
    public AlunoResponse atualizar(Integer id, AlunoRequest request) {

        Aluno aluno = buscarAluno(id);

        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setMedia(request.media());

        return AlunoResponse.from(aluno);
    }
    
}
