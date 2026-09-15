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

    // ============================================================
    // LISTAGEM
    // ============================================================

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

    // ============================================================
    // BUSCAR POR ID
    // ============================================================

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

    // ============================================================
    // ALUNO NÃO ENCONTRADO
    // ============================================================

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

    // ============================================================
    // INCLUSÃO
    // ============================================================

    @Test
    @DisplayName("Deve retornar status 201 e o aluno criado")
    void deveIncluirAluno() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Carlos",
                "carlos@empresa.com",
                "123456",
                LocalDate.of(1995, 5, 20),
                9.5
        );

        AlunoResponse resposta = new AlunoResponse(
                4,
                "Carlos",
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
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(
                        jsonPath("$.email")
                                .value("carlos@empresa.com")
                )
                .andExpect(jsonPath("$.media").value(9.5));

        verify(alunoService, times(1))
                .incluir(any(AlunoRequest.class));
    }

    // ============================================================
    // ATUALIZAÇÃO
    // ============================================================

    @Test
    @DisplayName("Deve retornar status 200 e o aluno atualizado")
    void deveAtualizarAluno() throws Exception {

        AlunoRequest request = new AlunoRequest(
                "Loester Atualizado",
                "novo@empresa.com",
                "123456",
                LocalDate.of(1990, 9, 11),
                10.0
        );

        AlunoResponse resposta = new AlunoResponse(
                1,
                "Loester Atualizado",
                "novo@empresa.com",
                LocalDate.of(1990, 9, 11),
                10.0
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
                                .value("Loester Atualizado")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("novo@empresa.com")
                )
                .andExpect(jsonPath("$.media").value(10.0));

        verify(alunoService, times(1))
                .atualizar(
                        eq(1),
                        any(AlunoRequest.class)
                );
    }

    // ============================================================
    // EXCLUSÃO
    // ============================================================

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

    // ============================================================
    // ALTERAR SENHA
    // ============================================================

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
}
