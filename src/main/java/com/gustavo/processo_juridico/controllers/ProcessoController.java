package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.CriarProcessoRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.usecases.processo.CriarProcessoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoController {
    private final CriarProcessoUseCase criarProcessoUseCase;

    public ProcessoController(CriarProcessoUseCase criarProcessoUseCase) {
        this.criarProcessoUseCase = criarProcessoUseCase;
    }

    @PostMapping
    public ResponseEntity<ProcessoResponse> criar(@Valid @RequestBody CriarProcessoRequest processoRequest) {
        ProcessoResponse processoResponse = criarProcessoUseCase.execute(processoRequest);
        return ResponseEntity.created(URI.create("/api/v1/processos/" + processoResponse.id())).body(processoResponse);
    }
}