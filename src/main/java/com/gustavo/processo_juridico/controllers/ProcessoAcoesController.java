package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Processos - Ações", description = "Criação e remoção de ações")
@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoAcoesController {
    private final RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;
    private final RemoverAcoesNoProcessoUseCase removerAcoesNoProcessoUseCase;
    private final ListarAcoesDoProcessoUseCase listarAcoesDoProcessoUseCase;

    public ProcessoAcoesController(
            RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase,
            RemoverAcoesNoProcessoUseCase removerAcoesNoProcessoUseCase,
            ListarAcoesDoProcessoUseCase listarAcoesDoProcessoUseCase
    ) {
        this.registrarAcoesNoProcessoUseCase = registrarAcoesNoProcessoUseCase;
        this.removerAcoesNoProcessoUseCase = removerAcoesNoProcessoUseCase;
        this.listarAcoesDoProcessoUseCase = listarAcoesDoProcessoUseCase;
    }

    @Operation(summary = "Registar ações de um processo")
    @PostMapping("/{id}/acoes")
    public ResponseEntity<Void> registrarAcoes(@PathVariable UUID id, @Valid @RequestBody RegistrarAcoesRequest acoesRequest) {
        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover ações de um processo")
    @DeleteMapping("/{id}/acoes")
    public ResponseEntity<Void> removerAcoes(@PathVariable UUID id, @Valid @RequestBody LoteIdsRequest idsAcoesRequest) {
        removerAcoesNoProcessoUseCase.execute(id, idsAcoesRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista ações do processo")
    @GetMapping("/{id}/acoes")
    public ResponseEntity<List<ProcessoResponse.Acao>> listarAcoes(@PathVariable UUID id) {
        return ResponseEntity.ok(listarAcoesDoProcessoUseCase.execute(id));
    }
}