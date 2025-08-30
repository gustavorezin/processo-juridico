package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResumoResponse;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BuscarProcessosPorParteUseCase {
    private final ProcessoRepository processoRepository;

    public BuscarProcessosPorParteUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public Page<ProcessoResumoResponse> execute(String cpfcnpj, Pageable pageable) {
        return processoRepository.findByParteCpfcnpj(cpfcnpj, pageable)
                .map(p -> new ProcessoResumoResponse(
                        p.getId(),
                        p.getNumero(),
                        p.getStatus(),
                        p.getDataAbertura()
                ));
    }

}
