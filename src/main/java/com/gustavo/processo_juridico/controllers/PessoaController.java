package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.pessoa.CriarPessoaRequest;
import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.usecases.pessoa.CriarPessoaUseCase;
import com.gustavo.processo_juridico.usecases.pessoa.ListarPessoasUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Pessoas")
@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {
    private final CriarPessoaUseCase criarPessoaUseCase;
    private final ListarPessoasUseCase listarPessoasUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase, ListarPessoasUseCase listarPessoasUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
        this.listarPessoasUseCase = listarPessoasUseCase;
    }

    @Operation(summary = "Cria uma nova pessoa")
    @PostMapping
    public ResponseEntity<PessoaResponse> criar(@Valid @RequestBody CriarPessoaRequest pessoaRequest) {
        PessoaResponse pessoaResponse = criarPessoaUseCase.execute(pessoaRequest);
        return ResponseEntity.created(URI.create("/api/v1/pessoas/" + pessoaResponse.id())).body(pessoaResponse);
    }

    @Operation(summary = "Lista pessoas")
    @GetMapping()
    public ResponseEntity<List<PessoaResponse>> listar() {
        return ResponseEntity.ok(listarPessoasUseCase.execute());
    }
}