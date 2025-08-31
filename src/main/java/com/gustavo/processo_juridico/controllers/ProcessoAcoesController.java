package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Processos - Ações", description = "Criação e remoção de ações")
@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoAcoesController {
    private final RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;
    private final RemoverAcoesNoProcessoUseCase removerAcoesNoProcessoUseCase;

    public ProcessoAcoesController(
            RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase,
            RemoverAcoesNoProcessoUseCase removerAcoesNoProcessoUseCase
    ) {
        this.registrarAcoesNoProcessoUseCase = registrarAcoesNoProcessoUseCase;
        this.removerAcoesNoProcessoUseCase = removerAcoesNoProcessoUseCase;
    }

    @Operation(summary = "Registar ações de um processo")
    @PostMapping("/{id}/acoes")
    public ResponseEntity<?> registrarAcoes(@PathVariable UUID id, @Valid @RequestBody RegistrarAcoesRequest acoesRequest) {
        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover ações de um processo")
    @DeleteMapping("/{id}/acoes")
    public ResponseEntity<Void> removerAcoes(@PathVariable UUID id, @Valid @RequestBody LoteIdsRequest idsAcoesRequest) {
        removerAcoesNoProcessoUseCase.execute(id, idsAcoesRequest);
        return ResponseEntity.ok().build();
    }
}