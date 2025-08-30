package com.gustavo.processo_juridico.usecases.processo;


import com.gustavo.processo_juridico.dtos.processo.ProcessoResumoResponse;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BuscarProcessosPorDataUseCase {
    private final ProcessoRepository processoRepository;

    public BuscarProcessosPorDataUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public Page<ProcessoResumoResponse> execute(LocalDate inicio, LocalDate fim, Pageable pageable) {
        return processoRepository.findByDataAberturaBetween(inicio, fim, pageable)
                .map(p -> new ProcessoResumoResponse(
                        p.getId(),
                        p.getNumero(),
                        p.getStatus(),
                        p.getDataAbertura()
                ));
    }

}
