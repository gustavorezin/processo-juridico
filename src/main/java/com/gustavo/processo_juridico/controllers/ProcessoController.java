package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.CriarProcessoRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoController {
    private final CriarProcessoUseCase criarProcessoUseCase;
    private final SuspenderProcessosUseCase suspenderProcessosUseCase;
    private final ArquivarProcessosUseCase arquivarProcessosUseCase;
    private final AtivarProcessosUseCase ativarProcessosUseCase;
    private final RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;

    public ProcessoController(
            CriarProcessoUseCase criarProcessoUseCase,
            SuspenderProcessosUseCase suspenderProcessosUseCase,
            ArquivarProcessosUseCase arquivarProcessosUseCase,
            AtivarProcessosUseCase ativarProcessosUseCase,
            RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase
    ) {
        this.criarProcessoUseCase = criarProcessoUseCase;
        this.suspenderProcessosUseCase = suspenderProcessosUseCase;
        this.arquivarProcessosUseCase = arquivarProcessosUseCase;
        this.ativarProcessosUseCase = ativarProcessosUseCase;
        this.registrarAcoesNoProcessoUseCase = registrarAcoesNoProcessoUseCase;
    }

    @PostMapping
    public ResponseEntity<ProcessoResponse> criar(@Valid @RequestBody CriarProcessoRequest processoRequest) {
        ProcessoResponse processoResponse = criarProcessoUseCase.execute(processoRequest);
        return ResponseEntity.created(URI.create("/api/v1/processos/" + processoResponse.id())).body(processoResponse);
    }

    @PostMapping("/suspender")
    public ResponseEntity<?> suspender(@RequestBody LoteIdsRequest ids) {
        suspenderProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/arquivar")
    public ResponseEntity<?> arquivar(@RequestBody LoteIdsRequest ids) {
        arquivarProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ativar")
    public ResponseEntity<?> ativar(@RequestBody LoteIdsRequest ids) {
        ativarProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/acoes")
    public ResponseEntity<?> registrarAcoes(@PathVariable UUID id, @RequestBody RegistrarAcoesRequest acoesRequest) {
        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);
        return ResponseEntity.ok().build();
    }
}