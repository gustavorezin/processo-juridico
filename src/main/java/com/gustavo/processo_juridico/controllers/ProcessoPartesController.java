package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.PartesRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.usecases.processo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Processos - Partes", description = "Criação e remoção de partes")
@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoPartesController {
    private final AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase;
    private final RemoverPartesNoProcessoUseCase removerPartesNoProcessoUseCase;
    private final ListarPartesDoProcessoUseCase listarPartesDoProcessoUseCase;

    public ProcessoPartesController(
            AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase,
            RemoverPartesNoProcessoUseCase removerPartesNoProcessoUseCase,
            ListarPartesDoProcessoUseCase listarPartesDoProcessoUseCase
    ) {
        this.adicionarPartesNoProcessoUseCase = adicionarPartesNoProcessoUseCase;
        this.removerPartesNoProcessoUseCase = removerPartesNoProcessoUseCase;
        this.listarPartesDoProcessoUseCase = listarPartesDoProcessoUseCase;
    }

    @Operation(summary = "Adiciona partes de um processo")
    @PostMapping("/{id}/partes")
    public ResponseEntity<Void> adicionarPartes(@PathVariable UUID id, @Valid @RequestBody PartesRequest partesRequest) {
        adicionarPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove partes de um processo")
    @DeleteMapping("/{id}/partes")
    public ResponseEntity<Void> removerPartes(@PathVariable UUID id, @Valid @RequestBody PartesRequest partesRequest) {
        removerPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista partes do processo")
    @GetMapping("/{id}/partes")
    public ResponseEntity<List<ProcessoResponse.Parte>> listarPartes(@PathVariable UUID id) {
        return ResponseEntity.ok(listarPartesDoProcessoUseCase.execute(id));
    }

}