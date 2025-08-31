package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.*;
import com.gustavo.processo_juridico.usecases.processo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Processos", description = "Criação e alterações de estado")
@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoController {
    private final CriarProcessoUseCase criarProcessoUseCase;
    private final SuspenderProcessosUseCase suspenderProcessosUseCase;
    private final ArquivarProcessosUseCase arquivarProcessosUseCase;
    private final AtivarProcessosUseCase ativarProcessosUseCase;

    public ProcessoController(
            CriarProcessoUseCase criarProcessoUseCase,
            SuspenderProcessosUseCase suspenderProcessosUseCase,
            ArquivarProcessosUseCase arquivarProcessosUseCase,
            AtivarProcessosUseCase ativarProcessosUseCase,
            AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase
    ) {
        this.criarProcessoUseCase = criarProcessoUseCase;
        this.suspenderProcessosUseCase = suspenderProcessosUseCase;
        this.arquivarProcessosUseCase = arquivarProcessosUseCase;
        this.ativarProcessosUseCase = ativarProcessosUseCase;
    }

    @Operation(summary = "Cria um novo processo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "409", description = "Número de processo duplicado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProcessoResponse> criar(@Valid @RequestBody CriarProcessoRequest processoRequest) {
        ProcessoResponse processoResponse = criarProcessoUseCase.execute(processoRequest);
        return ResponseEntity.created(URI.create("/api/v1/processos/" + processoResponse.id())).body(processoResponse);
    }

    @Operation(summary = "Suspende processos em lote")
    @PostMapping("/suspender")
    public ResponseEntity<?> suspender(@Valid @RequestBody LoteIdsRequest ids) {
        suspenderProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Arquiva processos em lote")
    @PostMapping("/arquivar")
    public ResponseEntity<?> arquivar(@Valid @RequestBody LoteIdsRequest ids) {
        arquivarProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Ativa processos em lote")
    @PostMapping("/ativar")
    public ResponseEntity<?> ativar(@Valid @RequestBody LoteIdsRequest ids) {
        ativarProcessosUseCase.execute(ids);
        return ResponseEntity.ok().build();
    }
}