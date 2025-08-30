package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.stereotype.Component;

@Component
public class ArquivarProcessosUseCase {
    private final ProcessoRepository processoRepository;

    public ArquivarProcessosUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public void execute(LoteIdsRequest idsRequest) {
        idsRequest.ids().forEach(id -> {
            Processo processo = processoRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Processo não encontrado")
            );
            processo.arquivar();
            processoRepository.save(processo);
        });
    }

}
