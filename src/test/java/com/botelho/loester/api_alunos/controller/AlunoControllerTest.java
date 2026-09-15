package com.botelho.loester.api_alunos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.botelho.loester.api_alunos.dto.request.AlteraSenhaRequest;
import com.botelho.loester.api_alunos.dto.request.AlunoRequest;
import com.botelho.loester.api_alunos.dto.response.AlunoResponse;
import com.botelho.loester.api_alunos.exception.RegistroNaoEncontradoException;
import com.botelho.loester.api_alunos.service.AlunoService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(AlunoController.class)
@DisplayName("Testes do AlunoController")
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AlunoService alunoService;

    private AlunoRequest alunoRequestValido() {

        return new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
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
    @DisplayName("Deve retornar status 200 e a lista de alunos")
    void deveListarTodos() throws Exception {

        List<AlunoResponse> resposta = List.of(
                new AlunoResponse(
                        1,
                        "Loester Botelho",
                        "loester@empresa.com",
                        LocalDate.of(1990, 9, 11),
                        10.0
                )
        );

        when(alunoService.listarTodos())
                .thenReturn(resposta);

        mockMvc.perform(
                get("/alunos")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Loester Botelho"))
                .andExpect(jsonPath("$[0].email").value("loester@empresa.com"))
                .andExpect(jsonPath("$[0].media").value(10.0));

        verify(alunoService, times(1))
                .listarTodos();
    }

    @Test
    @DisplayName("Deve retornar status 200 e o aluno correspondente ao ID")
    void deveListarPorId() throws Exception {

        AlunoResponse resposta = new AlunoResponse(
                1,
                "Loester Botelho",
                "loester@empresa.com",
                LocalDate.of(1990, 9, 11),
                10.0
        );

        when(alunoService.obterPorId(1))
                .thenReturn(resposta);

        mockMvc.perform(
                get("/alunos/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Loester Botelho"))
                .andExpect(jsonPath("$.email").value("loester@empresa.com"))
                .andExpect(jsonPath("$.media").value(10.0));

        verify(alunoService, times(1))
                .obterPorId(1);
    }

    @Test
    @DisplayName("Deve retornar status 404 quando o aluno não for encontrado")
    void deveRetornar404QuandoAlunoNaoEncontrado() throws Exception {

        when(alunoService.obterPorId(99))
                .thenThrow(
                        new RegistroNaoEncontradoException(
                                "Aluno não encontrado. Id: 99"
                        )
                );

        mockMvc.perform(
                get("/alunos/99")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(
                        jsonPath("$.mensagem")
                                .value("Aluno não encontrado. Id: 99")
                );

        verify(alunoService, times(1))
                .obterPorId(99);
    }

    @Test
    @DisplayName("Deve retornar status 201 e o aluno criado")
    void deveIncluirAluno() throws Exception {

        AlunoRequest request = alunoRequestValido();

        AlunoResponse resposta = new AlunoResponse(
                4,
                "Carlos Silva",
                "carlos@empresa.com",
                LocalDate.of(1995, 5, 20),
                9.5
        );

        when(alunoService.incluir(any(AlunoRequest.class)))
                .thenReturn(resposta);

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.nome").value("Carlos Silva"))
                .andExpect(
                        jsonPath("$.email")
                                .value("carlos@empresa.com")
                )
                .andExpect(jsonPath("$.media").value(9.5));

        verify(alunoService, times(1))
                .incluir(any(AlunoRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 400 quando os dados forem inválidos")
    void deveRetornar400QuandoDadosInvalidos() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "",
                "email-invalido",
                "123",
                LocalDate.now().plusDays(1),
                15.0,
                "123",
                "999",
                "123",
                -1,
                0.1,
                -10.0,
                -1,
                LocalDate.now().plusDays(1),
                LocalDate.now().minusDays(1),
                "site-invalido",
                "Observação válida",
                null
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(alunoService, times(0))
                .incluir(any(AlunoRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 e o aluno atualizado")
    void deveAtualizarAluno() throws Exception {

        AlunoRequest request = alunoRequestValido();

        AlunoResponse resposta = new AlunoResponse(
                1,
                "Carlos Silva",
                "carlos@empresa.com",
                LocalDate.of(1995, 5, 20),
                9.5
        );

        when(
                alunoService.atualizar(
                        eq(1),
                        any(AlunoRequest.class)
                )
        )
                .thenReturn(resposta);

        mockMvc.perform(
                put("/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(
                        jsonPath("$.nome")
                                .value("Carlos Silva")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("carlos@empresa.com")
                )
                .andExpect(jsonPath("$.media").value(9.5));

        verify(alunoService, times(1))
                .atualizar(
                        eq(1),
                        any(AlunoRequest.class)
                );
    }

    @Test
    @DisplayName("Deve retornar status 400 ao atualizar com dados inválidos")
    void deveRetornar400AoAtualizarDadosInvalidos() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "A",
                "email",
                "123",
                LocalDate.now().plusDays(1),
                -1.0,
                "123",
                "999",
                "123",
                -1,
                0.1,
                -10.0,
                -1,
                LocalDate.now().plusDays(1),
                LocalDate.now().minusDays(1),
                "site-invalido",
                "Observação válida",
                null
        );

        mockMvc.perform(
                put("/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(alunoService, times(0))
                .atualizar(
                        eq(1),
                        any(AlunoRequest.class)
                );
    }

    @Test
    @DisplayName("Deve retornar status 204 ao deletar um aluno")
    void deveDeletarAluno() throws Exception {

        doNothing()
                .when(alunoService)
                .deletar(1);

        mockMvc.perform(
                delete("/alunos/1")
        )
                .andExpect(status().isNoContent());

        verify(alunoService, times(1))
                .deletar(1);
    }

    @Test
    @DisplayName("Deve retornar status 204 ao alterar a senha")
    void deveAlterarSenha() throws Exception {

        AlteraSenhaRequest request =
                new AlteraSenhaRequest("NovaSenha123!");

        doNothing()
                .when(alunoService)
                .alterarSenha(
                        eq(1),
                        any(AlteraSenhaRequest.class)
                );

        mockMvc.perform(
                patch("/alunos/1/senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isNoContent());

        verify(alunoService, times(1))
                .alterarSenha(
                        eq(1),
                        any(AlteraSenhaRequest.class)
                );
    }

    @Test
    @DisplayName("Deve retornar 400 para CPF inválido")
    void deveRetornar400ParaCpfInvalido() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "12345678909",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(alunoService, times(0))
                .incluir(any(AlunoRequest.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para telefone inválido")
    void deveRetornar400ParaTelefoneInvalido() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "47999999999",
                "89000-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(alunoService, times(0))
                .incluir(any(AlunoRequest.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para CEP inválido")
    void deveRetornar400ParaCepInvalido() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(alunoService, times(0))
                .incluir(any(AlunoRequest.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para idade inválida")
    void deveRetornar400ParaIdadeInvalida() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                121,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 para altura inválida")
    void deveRetornar400ParaAlturaInvalida() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                0.40,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 para peso inválido")
    void deveRetornar400ParaPesoInvalido() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                501.0,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 para quantidade de dependentes negativa")
    void deveRetornar400ParaDependentesInvalidos() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                80.50,
                -1,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 para data de matrícula futura")
    void deveRetornar400ParaDataMatriculaFutura() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 para site inválido")
    void deveRetornar400ParaSiteInvalido() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "www.exemplo.com",
                "Aluno válido.",
                true
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("Deve retornar 400 quando ativo for nulo")
    void deveRetornar400QuandoAtivoForNulo() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos Silva",
                "carlos@empresa.com",
                "Senha123!",
                LocalDate.of(1995, 5, 20),
                9.50,
                "123.456.789-09",
                "(47) 99999-9999",
                "89000-000",
                30,
                1.75,
                80.50,
                2,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                "https://www.exemplo.com",
                "Aluno válido.",
                null
        );

        mockMvc.perform(
                post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
