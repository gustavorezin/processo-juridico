package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResumoResponse;
import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BuscarProcessosPorStatusUseCase {
    private final ProcessoRepository processoRepository;

    public BuscarProcessosPorStatusUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public Page<ProcessoResumoResponse> execute(StatusProcesso status, Pageable pageable) {
        return processoRepository.findByStatus(status, pageable)
                .map(p -> new ProcessoResumoResponse(
                        p.getId(),
                        p.getNumero(),
                        p.getStatus(),
                        p.getDataAbertura()
                ));
    }

}
