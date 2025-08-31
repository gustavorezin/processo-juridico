package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.pessoa.CriarPessoaRequest;
import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.usecases.pessoa.CriarPessoaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Pessoas")
@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {
    private final CriarPessoaUseCase criarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
    }

    @Operation(summary = "Cria uma nova pessoa")
    @PostMapping
    public ResponseEntity<PessoaResponse> criar(@Valid @RequestBody CriarPessoaRequest pessoaRequest) {
        PessoaResponse pessoaResponse = criarPessoaUseCase.execute(pessoaRequest);
        return ResponseEntity.created(URI.create("/api/v1/pessoas/" + pessoaResponse.id())).body(pessoaResponse);
    }
}