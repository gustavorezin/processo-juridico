package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoAcoesController {
    private final RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;

    public ProcessoAcoesController(RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase) {
        this.registrarAcoesNoProcessoUseCase = registrarAcoesNoProcessoUseCase;
    }

    @PostMapping("/{id}/acoes")
    public ResponseEntity<?> registrarAcoes(@PathVariable UUID id, @RequestBody RegistrarAcoesRequest acoesRequest) {
        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);
        return ResponseEntity.ok().build();
    }
}