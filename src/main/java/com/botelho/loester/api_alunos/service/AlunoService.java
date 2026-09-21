package com.botelho.loester.api_alunos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.EmailJaCadastradoException;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;
import com.botelho.loester.api_alunos.mapper.AlunoMapper;
import com.botelho.loester.api_alunos.model.Aluno;

@Service
public class AlunoService {

    private final AlunoMapper mapper;

    private final List<Aluno> lista = new ArrayList<>();

    public AlunoService(AlunoMapper mapper) {

        this.mapper = mapper;

        Aluno aluno1 = new Aluno(
                1,
                "Loester Botelho",
                "loester.botelho@empresa.com",
                "P@ssW0rd#2026!",
                LocalDate.of(1990, 9, 11),
                10.0,
                "123.456.789-09",
                "(47) 99999-9999",
                "89120-000",
                35,
                1.75,
                80.50,
                2,
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2028, 12, 20),
                "https://www.exemplo.com",
                "Aluno de Ciência da Computação.",
                true
        );

        Aluno aluno2 = new Aluno(
                2,
                "Henrique",
                "henrique.developer@empresa.com",
                "1234567",
                LocalDate.of(2000, 9, 11),
                8.75,
                "987.654.321-00",
                "(47) 98888-8888",
                "89120-001",
                26,
                1.80,
                82.00,
                1,
                LocalDate.of(2026, 2, 15),
                LocalDate.of(2028, 12, 20),
                "https://www.henrique.com",
                "Aluno cadastrado para testes.",
                true
        );

        Aluno aluno3 = new Aluno(
                3,
                "Heloisa",
                "heloisa.developer@empresa.com",
                "1234",
                LocalDate.of(1970, 9, 11),
                8.75,
                "111.222.333-44",
                "(47) 97777-7777",
                "89120-002",
                56,
                1.65,
                65.00,
                0,
                LocalDate.of(2026, 3, 20),
                LocalDate.of(2028, 12, 20),
                "https://www.heloisa.com",
                "Professora de Java.",
                true
        );

        lista.add(aluno1);
        lista.add(aluno2);
        lista.add(aluno3);
    }

    public List<AlunoResponse> listarTodos() {

        return mapper.toResponseList(lista);
    }

    public AlunoResponse obterPorId(Integer id) {

        return lista.stream()
                .filter(aluno -> aluno.getId().equals(id))
                .findFirst()
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Aluno não encontrado. Id: " + id
                        )
                );
    }

    public AlunoResponse incluir(AlunoRequest request) {

        validarEmailParaInclusao(request.email());

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
                request.media(),
                request.cpf(),
                request.telefone(),
                request.cep(),
                request.idade(),
                request.altura(),
                request.peso(),
                request.quantidadeDependentes(),
                request.dataMatricula(),
                request.dataConclusao(),
                request.site(),
                request.observacao(),
                request.ativo()
        );

        lista.add(aluno);

        return mapper.toResponse(aluno);
    }

    public AlunoResponse atualizar(Integer id, AlunoRequest request) {

        Aluno aluno = buscarAluno(id);

        validarEmailParaAtualizacao(request.email(), id);

        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setMedia(request.media());
        aluno.setCpf(request.cpf());
        aluno.setTelefone(request.telefone());
        aluno.setCep(request.cep());
        aluno.setIdade(request.idade());
        aluno.setAltura(request.altura());
        aluno.setPeso(request.peso());
        aluno.setQuantidadeDependentes(request.quantidadeDependentes());
        aluno.setDataMatricula(request.dataMatricula());
        aluno.setDataConclusao(request.dataConclusao());
        aluno.setSite(request.site());
        aluno.setObservacao(request.observacao());
        aluno.setAtivo(request.ativo());

        return mapper.toResponse(aluno);
    }

    public void deletar(Integer id) {

        Aluno aluno = buscarAluno(id);

        lista.remove(aluno);
    }

    public void alterarSenha(Integer id, AlteraSenhaRequest request) {

        Aluno aluno = buscarAluno(id);

        aluno.setSenha(request.novaSenha());
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

    private void validarEmailParaInclusao(String email) {

        boolean emailExiste = lista.stream()
                .anyMatch(aluno ->
                        aluno.getEmail().equalsIgnoreCase(email)
                );

        if (emailExiste) {

            throw new EmailJaCadastradoException(
                    "E-mail já cadastrado: " + email
            );
        }
    }

    private void validarEmailParaAtualizacao(
            String email,
            Integer idAluno) {

        boolean emailExiste = lista.stream()
                .anyMatch(aluno ->
                        aluno.getEmail().equalsIgnoreCase(email)
                        && !aluno.getId().equals(idAluno)
                );

        if (emailExiste) {

            throw new EmailJaCadastradoException(
                    "E-mail já cadastrado para outro aluno: " + email
            );
        }
    }
}
