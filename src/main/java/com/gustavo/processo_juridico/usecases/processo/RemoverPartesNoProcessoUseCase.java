package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.PartesRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemoverPartesNoProcessoUseCase {
    private final ProcessoRepository processoRepository;

    public RemoverPartesNoProcessoUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Transactional
    public void execute(UUID processoId, PartesRequest partesRequest) {
        Processo processo = processoRepository.findById(processoId).orElseThrow(() ->
                new IllegalArgumentException("Processo não encontrado")
        );

        partesRequest.partes().forEach(p ->
                processo.removerParte(p.pessoaId(), p.tipo())
        );

        processoRepository.save(processo);
    }
}
