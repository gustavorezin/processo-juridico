package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.*;
import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import com.gustavo.processo_juridico.usecases.processo.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoController {
    private final CriarProcessoUseCase criarProcessoUseCase;
    private final SuspenderProcessosUseCase suspenderProcessosUseCase;
    private final ArquivarProcessosUseCase arquivarProcessosUseCase;
    private final AtivarProcessosUseCase ativarProcessosUseCase;
    private final RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;
    private final AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase;
    private final BuscarProcessosPorStatusUseCase buscarProcessosPorStatusUseCase;
    private final BuscarProcessosPorParteUseCase buscarProcessosPorParteUseCase;
    private final BuscarProcessosPorDataUseCase buscarProcessosPorDataUseCase;

    public ProcessoController(
            CriarProcessoUseCase criarProcessoUseCase,
            SuspenderProcessosUseCase suspenderProcessosUseCase,
            ArquivarProcessosUseCase arquivarProcessosUseCase,
            AtivarProcessosUseCase ativarProcessosUseCase,
            RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase,
            AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase,
            BuscarProcessosPorStatusUseCase buscarProcessosPorStatusUseCase,
            BuscarProcessosPorParteUseCase buscarProcessosPorParteUseCase,
            BuscarProcessosPorDataUseCase buscarProcessosPorDataUseCase
    ) {
        this.criarProcessoUseCase = criarProcessoUseCase;
        this.suspenderProcessosUseCase = suspenderProcessosUseCase;
        this.arquivarProcessosUseCase = arquivarProcessosUseCase;
        this.ativarProcessosUseCase = ativarProcessosUseCase;
        this.registrarAcoesNoProcessoUseCase = registrarAcoesNoProcessoUseCase;
        this.adicionarPartesNoProcessoUseCase = adicionarPartesNoProcessoUseCase;
        this.buscarProcessosPorStatusUseCase = buscarProcessosPorStatusUseCase;
        this.buscarProcessosPorParteUseCase = buscarProcessosPorParteUseCase;
        this.buscarProcessosPorDataUseCase = buscarProcessosPorDataUseCase;
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

    @PostMapping("/{id}/partes")
    public ResponseEntity<?> adicionarPartes(@PathVariable UUID id, @RequestBody AdicionarPartesRequest partesRequest) {
        adicionarPartesNoProcessoUseCase.execute(id, partesRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Page<ProcessoResumoResponse>> findByStatus(@RequestParam StatusProcesso status, Pageable pageable) {
        return ResponseEntity.ok(buscarProcessosPorStatusUseCase.execute(status, pageable));
    }

    @GetMapping("/cpfcnpj")
    public ResponseEntity<Page<ProcessoResumoResponse>> findByParte(@RequestParam String cpfcnpj, Pageable pageable) {
        return ResponseEntity.ok(buscarProcessosPorParteUseCase.execute(cpfcnpj, pageable));
    }

    @GetMapping("/data-abertura")
    public ResponseEntity<Page<ProcessoResumoResponse>> findByDataAbertura(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buscarProcessosPorDataUseCase.execute(inicio, fim, pageable));
    }
}