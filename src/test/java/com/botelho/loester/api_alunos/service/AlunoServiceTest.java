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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.EmailCadastradoException;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;
import com.botelho.loester.api_alunos.mapper.AlunoMapper;
import com.botelho.loester.api_alunos.model.Aluno;

@DisplayName("Testes unitários do AlunoService")
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
    @DisplayName("Deve cadastrar um aluno quando os dados forem válidos")
    void deveCadastrarAlunoQuandoDadosForemValidos() {
        // Arrange
        AlunoRequest request = alunoRequestValido();

        // Act
        AlunoResponse response = alunoService.incluir(request);

        // Assert
        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.id())
                .isGreaterThan(0);
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar dois alunos com o mesmo e-mail")
    void deveLancarExcecaoAoCadastrarDoisAlunosComMesmoEmail() {
        // Arrange
        AlunoRequest primeiroAluno = alunoRequestValido();

        AlunoRequest segundoAluno = new AlunoRequest(
                "Outro Aluno",
                primeiroAluno.email(),
                "OutraSenha@123",
                LocalDate.of(1998, 4, 10),
                8.0,
                "987.654.321-00",
                "(47) 98888-8888",
                "89120-001",
                28,
                1.80,
                75.0,
                1,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.outroaluno.com",
                "Teste de e-mail duplicado.",
                true
        );

        // Act
        AlunoResponse response = alunoService.incluir(primeiroAluno);

        // Assert
        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThatThrownBy(
                () -> alunoService.incluir(segundoAluno)
        ).isInstanceOf(EmailCadastradoException.class);
    }

    @Test
    @DisplayName("Deve listar todos os alunos cadastrados inicialmente")
    void deveListarTodosOsAlunos() {
        // Act
        List<AlunoResponse> listaAlunos = alunoService.listarTodos();

        // Assert
        assertNotNull(listaAlunos);
        assertEquals(3, listaAlunos.size());
        assertEquals("Loester Botelho", listaAlunos.get(0).nome());
    }

    @Test
    @DisplayName("Deve retornar o aluno correspondente ao ID informado")
    void deveListarAlunoPorId() {
        // Act
        AlunoResponse aluno = alunoService.obterPorId(1);

        // Assert
        assertNotNull(aluno);
        assertEquals(1, aluno.id());
        assertEquals("Loester Botelho", aluno.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o aluno não for encontrado pelo ID")
    void deveLancarExcecaoQuandoAlunoNaoEncontradoPorId() {
        // Arrange
        Integer idInexistente = 99;

        // Act
        RegistroNaoEncontradoException exception = assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.obterPorId(idInexistente)
        );

        // Assert
        assertEquals(
                "Aluno não encontrado. Id: " + idInexistente,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Deve incluir um novo aluno com sucesso e incrementar o ID")
    void deveIncluirNovoAluno() {
        // Arrange
        AlunoRequest request = alunoRequestValido();

        // Act
        AlunoResponse response = alunoService.incluir(request);

        // Assert
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
        // Arrange
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

        // Act
        AlunoResponse response = alunoService.atualizar(1, request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Loester Atualizado", response.nome());
        assertEquals("loester.novo@empresa.com", response.email());
        assertEquals(LocalDate.of(1990, 9, 11), response.dataNascimento());
        assertEquals(10.0, response.media());
    }

    @Test
    @DisplayName("Deve alterar a senha do aluno sem lançar exceção")
    void deveAlterarSenha() {
        // Arrange
        AlteraSenhaRequest request =
                new AlteraSenhaRequest("NovaSenhaSuperSegura!#");

        // Act e Assert
        assertDoesNotThrow(
                () -> alunoService.alterarSenha(1, request)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um aluno inexistente")
    void deveLancarExcecaoAoAtualizarAlunoInexistente() {
        // Arrange
        AlunoRequest request = alunoRequestValido();

        // Act e Assert
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.atualizar(99, request)
        );
    }

    @Test
    @DisplayName("Deve deletar o aluno com sucesso quando o ID existir")
    void deveDeletarAluno() {
        // Act
        alunoService.deletar(3);

        // Assert
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.obterPorId(3)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um aluno inexistente")
    void deveLancarExcecaoAoDeletarAlunoInexistente() {
        // Act e Assert
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> alunoService.deletar(99)
        );
    }

    @Test
    @DisplayName("Deve incluir aluno com todos os campos preenchidos")
    void deveIncluirAlunoComTodosOsCampos() {
        // Arrange
        AlunoRequest request = alunoRequestValido();

        // Act
        AlunoResponse response = alunoService.incluir(request);

        // Assert
        assertNotNull(response);
        assertEquals(4, response.id());
        assertEquals("Carlos Silva", response.nome());
        assertEquals("carlos.silva@empresa.com", response.email());
        assertEquals(9.5, response.media());
    }

    @Test
    @DisplayName("Deve atualizar todos os campos do aluno")
    void deveAtualizarTodosOsCamposDoAluno() {
        // Arrange
        AlunoRequest request = alunoRequestValido();

        // Act
        AlunoResponse response = alunoService.atualizar(1, request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Carlos Silva", response.nome());
        assertEquals("carlos.silva@empresa.com", response.email());
        assertEquals(9.5, response.media());
    }
}