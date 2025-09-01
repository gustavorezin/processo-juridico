package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AtivarProcessosUseCase {
    private final ProcessoRepository processoRepository;

    public AtivarProcessosUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Transactional
    public void execute(LoteIdsRequest idsRequest) {
        idsRequest.ids().forEach(id -> {
            Processo processo = processoRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Processo não encontrado")
            );
            processo.ativar();
            processoRepository.save(processo);
        });
    }

}
