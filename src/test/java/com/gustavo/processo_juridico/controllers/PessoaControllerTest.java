package com.gustavo.processo_juridico.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.processo_juridico.dtos.pessoa.CriarPessoaRequest;
import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.usecases.pessoa.CriarPessoaUseCase;
import com.gustavo.processo_juridico.usecases.pessoa.ListarPessoasUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CriarPessoaUseCase criarPessoaUseCase;

    @MockitoBean
    ListarPessoasUseCase listarPessoasUseCase;

    @Test
    void deveCriarPessoaRetornar201() throws Exception {
        UUID id = UUID.randomUUID();
        PessoaResponse pessoaResponse = new PessoaResponse(id, "Teste", "111", "a@a.com", "999");
        when(criarPessoaUseCase.execute(any(CriarPessoaRequest.class))).thenReturn(pessoaResponse);

        CriarPessoaRequest pessoaRequest = new CriarPessoaRequest("Teste", "111", "a@a.com", "999");

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/pessoas/" + id))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.cpfcnpj").value("111"));
    }

    @Test
    void deveRetornar400QuandoRequestInvalido() throws Exception {
        CriarPessoaRequest pessoaRequest = new CriarPessoaRequest("", "111", "a@a.com", "999");

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequest)))
                .andExpect(status().isBadRequest());
    }
}
