package com.botelho.loester.api_alunos.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;

@DisplayName("Testes Unitários para AlunoService")
class AlunoServiceTest {

    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        alunoService = new AlunoService();
    }

    @Nested
    @DisplayName("Testes de Listagem")
    class ListagemTests {

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
            AlunoResponse aluno = alunoService.listar(1);

            assertNotNull(aluno);
            assertEquals(1, aluno.id());
            assertEquals("Loester Botelho", aluno.nome());
        }

        @Test
        @DisplayName("Deve lançar exceção quando o ID do aluno não for encontrado na listagem")
        void deveLancarExcecaoQuandoAlunoNaoEncontradoPorId() {
            Integer idInexistente = 99;

            RegistroNaoEncontradoException exception = assertThrows(
                    RegistroNaoEncontradoException.class,
                    () -> alunoService.listar(idInexistente)
            );

            assertEquals("Aluno não encontrado. Id: " + idInexistente, exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes de Inclusão")
    class InclusaoTests {

        @Test
        @DisplayName("Deve incluir um novo aluno com sucesso e incrementar o ID")
        void deveIncluirNovoAluno() {
            AlunoRequest request = new AlunoRequest(
                    "Carlos Silva",
                    "carlos.silva@empresa.com",
                    "SenhaSegura@123",
                    LocalDate.of(1995, 5, 20),
                    9.5
            );

            AlunoResponse response = alunoService.incluir(request);

            assertNotNull(response);
            assertEquals(4, response.id());
            assertEquals("Carlos Silva", response.nome());
            assertEquals("carlos.silva@empresa.com", response.email());
        }
    }

    @Nested
    @DisplayName("Testes de Atualização e Alteração")
    class AtualizacaoTests {

        @Test
        @DisplayName("Deve atualizar os dados do aluno com sucesso")
        void deveAtualizarAluno() {
            AlunoRequest request = new AlunoRequest(
                    "Loester Atualizado",
                    "loester.novo@empresa.com",
                    "IgnoradaNoUpdate",
                    LocalDate.of(1990, 9, 11),
                    10.0
            );

            AlunoResponse response = alunoService.atualizar(1, request);

            assertNotNull(response);
            assertEquals("Loester Atualizado", response.nome());
            assertEquals("loester.novo@empresa.com", response.email());
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
            AlunoRequest request = new AlunoRequest(
                    "Teste", "teste@teste.com", "123", LocalDate.now(), 5.0
            );

            assertThrows(
                    RegistroNaoEncontradoException.class,
                    () -> alunoService.atualizar(99, request)
            );
        }
    }

    @Nested
    @DisplayName("Testes de Exclusão")
    class DelecaoTests {

        @Test
        @DisplayName("Deve deletar o aluno com sucesso quando o ID existir")
        void deveDeletarAluno() {
            alunoService.deletar(3);

            assertThrows(
                    RegistroNaoEncontradoException.class,
                    () -> alunoService.listar(3)
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
    }
}