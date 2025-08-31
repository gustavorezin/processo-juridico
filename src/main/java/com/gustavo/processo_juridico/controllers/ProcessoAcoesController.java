package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.usecases.processo.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping("/{id}/acoes")
    public ResponseEntity<?> registrarAcoes(@PathVariable UUID id, @Valid @RequestBody RegistrarAcoesRequest acoesRequest) {
        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/acoes")
    public ResponseEntity<Void> removerAcoes(@PathVariable UUID id, @Valid @RequestBody LoteIdsRequest idsAcoesRequest) {
        removerAcoesNoProcessoUseCase.execute(id, idsAcoesRequest);
        return ResponseEntity.ok().build();
    }
}