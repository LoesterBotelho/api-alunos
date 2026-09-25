package com.botelho.loester.api_alunos.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;
import com.botelho.loester.api_alunos.mapper.AlunoMapper;
import com.botelho.loester.api_alunos.model.Aluno;

@DisplayName("Testes Unitários para AlunoService")
class AlunoServiceTest {

    private AlunoService alunoService;
    private AlunoMapper alunoMapper;

    @BeforeEach
    void setUp() {
        alunoMapper = mock(AlunoMapper.class);
        alunoService = new AlunoService(alunoMapper);

        configurarMapper();
    }

    private void configurarMapper() {
        AlunoResponse loester = new AlunoResponse(
                1,
                "Loester Botelho",
                "loester@empresa.com",
                LocalDate.of(1990, 9, 11),
                10.0
        );

        AlunoResponse henrique = new AlunoResponse(
                2,
                "Henrique",
                "henrique@empresa.com",
                LocalDate.of(2000, 9, 11),
                8.75
        );

        AlunoResponse terceiro = new AlunoResponse(
                3,
                "Carlos",
                "carlos@empresa.com",
                LocalDate.of(1995, 5, 20),
                9.0
        );

        when(alunoMapper.toResponseList(anyList()))
                .thenReturn(List.of(loester, henrique, terceiro));

        when(alunoMapper.toResponse(any(Aluno.class)))
                .thenAnswer(invocation -> {
                    Aluno aluno = invocation.getArgument(0);
                    return new AlunoResponse(
                            aluno.getId(),
                            aluno.getNome(),
                            aluno.getEmail(),
                            aluno.getDataNascimento(),
                            aluno.getMedia()
                    );
                });
    }

    private AlunoRequest alunoRequestValido() {
        return new AlunoRequest(
                "Carlos Silva",
                "carlos.silva@empresa.com",
                "SenhaSegura@123",
                LocalDate.of(1995, 5, 20),
                9.5,
                "123.456.789-09",
                "(47) 99999-9999",
                "89120-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno cadastrado para testes.",
                true
        );
    }

    @Test
    @DisplayName("Deve listar todos os alunos cadastrados inicialmente")
    void deveListarTodosOsAlunos() {
        List<AlunoResponse> listaAlunos = alunoService.listarTodos();

        assertNotNull(listaAlunos);
        assertEquals(3, listaAlunos.size());
        assertEquals("Loester Botelho", listaAlunos.get(0).nome());
    }

    @Test
    @DisplayName("Deve retornar o aluno correspondente ao ID informado")
    void deveListarAlunoPorId() {
        AlunoResponse aluno = alunoService.obterPorId(1);

        assertNotNull(aluno);
        assertEquals(1, aluno.id());
        assertEquals("Loester Botelho", aluno.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID do aluno não for encontrado")
    void deveLancarExcecaoQuandoAlunoNaoEncontradoPorId() {
        Integer idInexistente = 99;

        RegistroNaoEncontradoException exception = assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.obterPorId(idInexistente)
        );

        assertEquals("Aluno não encontrado. Id: " + idInexistente, exception.getMessage());
    }

    @Test
    @DisplayName("Deve incluir um novo aluno com sucesso e incrementar o ID")
    void deveIncluirNovoAluno() {
        AlunoRequest request = alunoRequestValido();

        AlunoResponse response = alunoService.incluir(request);

        assertNotNull(response);
        assertEquals(4, response.id());
        assertEquals("Carlos Silva", response.nome());
        assertEquals("carlos.silva@empresa.com", response.email());
        assertEquals(LocalDate.of(1995, 5, 20), response.dataNascimento());
        assertEquals(9.5, response.media());
    }

    @Test
    @DisplayName("Deve atualizar os dados do aluno com sucesso")
    void deveAtualizarAluno() {
        AlunoRequest request = new AlunoRequest(
                "Loester Atualizado",
                "loester.novo@empresa.com",
                "IgnoradaNoUpdate",
                LocalDate.of(1990, 9, 11),
                10.0,
                "123.456.789-09",
                "(47) 99999-9999",
                "89120-000",
                35,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.loester.com",
                "Aluno atualizado.",
                true
        );

        AlunoResponse response = alunoService.atualizar(1, request);

        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Loester Atualizado", response.nome());
        assertEquals("loester.novo@empresa.com", response.email());
        assertEquals(LocalDate.of(1990, 9, 11), response.dataNascimento());
        assertEquals(10.0, response.media());
    }

    @Test
    @DisplayName("Deve alterar a senha do aluno com sucesso")
    void deveAlterarSenha() {
        AlteraSenhaRequest request = new AlteraSenhaRequest("NovaSenhaSuperSegura!#");

        assertDoesNotThrow(() -> alunoService.alterarSenha(1, request));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar aluno inexistente")
    void deveLancarExcecaoAoAtualizarAlunoInexistente() {
        AlunoRequest request = alunoRequestValido();

        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.atualizar(99, request)
        );
    }

    @Test
    @DisplayName("Deve deletar o aluno com sucesso quando o ID existir")
    void deveDeletarAluno() {
        alunoService.deletar(3);

        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.obterPorId(3)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar aluno inexistente")
    void deveLancarExcecaoAoDeletarAlunoInexistente() {
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.deletar(99)
        );
    }

    @Test
    @DisplayName("Deve incluir aluno com todos os novos campos")
    void deveIncluirAlunoComTodosOsCampos() {
        AlunoRequest request = alunoRequestValido();

        AlunoResponse response = alunoService.incluir(request);

        assertNotNull(response);
        assertEquals(4, response.id());
        assertEquals("Carlos Silva", response.nome());
        assertEquals("carlos.silva@empresa.com", response.email());
        assertEquals(9.5, response.media());
    }

    @Test
    @DisplayName("Deve atualizar todos os novos campos do aluno")
    void deveAtualizarTodosOsCamposDoAluno() {
        AlunoRequest request = alunoRequestValido();

        AlunoResponse response = alunoService.atualizar(1, request);

        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Carlos Silva", response.nome());
        assertEquals("carlos.silva@empresa.com", response.email());
        assertEquals(9.5, response.media());
    }
}