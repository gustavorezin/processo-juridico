package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.PartesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import jakarta.validation.Valid;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoPartesController {
    private final AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase;
    private final RemoverPartesNoProcessoUseCase removerPartesNoProcessoUseCase;

    public ProcessoPartesController(
            AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase,
            RemoverPartesNoProcessoUseCase removerPartesNoProcessoUseCase
    ) {
        this.adicionarPartesNoProcessoUseCase = adicionarPartesNoProcessoUseCase;
        this.removerPartesNoProcessoUseCase = removerPartesNoProcessoUseCase;
    }

    @PostMapping("/{id}/partes")
    public ResponseEntity<?> adicionarPartes(@PathVariable UUID id, @Valid @RequestBody PartesRequest partesRequest) {
        adicionarPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/partes")
    public ResponseEntity<?> removerPartes(@PathVariable UUID id, @Valid @RequestBody PartesRequest partesRequest) {
        removerPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.ok().build();
    }

}