package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.AdicionarPartesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoPartesController {
    private final AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase;

    public ProcessoPartesController(AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase) {
        this.adicionarPartesNoProcessoUseCase = adicionarPartesNoProcessoUseCase;
    }

    @PostMapping("/{id}/partes")
    public ResponseEntity<?> adicionarPartes(@PathVariable UUID id, @RequestBody AdicionarPartesRequest partesRequest) {
        adicionarPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.ok().build();
    }
}