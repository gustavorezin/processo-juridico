package com.gustavo.processo_juridico.controllers;

import com.gustavo.processo_juridico.dtos.processo.*;
import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import com.gustavo.processo_juridico.usecases.processo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoBuscaController {
    private final BuscarProcessosPorStatusUseCase buscarProcessosPorStatusUseCase;
    private final BuscarProcessosPorParteUseCase buscarProcessosPorParteUseCase;
    private final BuscarProcessosPorDataUseCase buscarProcessosPorDataUseCase;

    public ProcessoBuscaController(
            BuscarProcessosPorStatusUseCase buscarProcessosPorStatusUseCase,
            BuscarProcessosPorParteUseCase buscarProcessosPorParteUseCase,
            BuscarProcessosPorDataUseCase buscarProcessosPorDataUseCase
    ) {
        this.buscarProcessosPorStatusUseCase = buscarProcessosPorStatusUseCase;
        this.buscarProcessosPorParteUseCase = buscarProcessosPorParteUseCase;
        this.buscarProcessosPorDataUseCase = buscarProcessosPorDataUseCase;
    }

    @GetMapping("/status")
    public ResponseEntity<Page<ProcessoResumoResponse>> listarPorStatus(@RequestParam StatusProcesso status, Pageable pageable) {
        return ResponseEntity.ok(buscarProcessosPorStatusUseCase.execute(status, pageable));
    }

    @GetMapping("/cpfcnpj")
    public ResponseEntity<Page<ProcessoResumoResponse>> listarPorParte(@RequestParam String cpfcnpj, Pageable pageable) {
        return ResponseEntity.ok(buscarProcessosPorParteUseCase.execute(cpfcnpj, pageable));
    }

    @GetMapping("/data-abertura")
    public ResponseEntity<Page<ProcessoResumoResponse>> listarPorDataAbertura(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            Pageable pageable
    ) {
        return ResponseEntity.ok(buscarProcessosPorDataUseCase.execute(inicio, fim, pageable));
    }
}