package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemoverAcoesNoProcessoUseCase {
    private final ProcessoRepository processoRepository;

    public RemoverAcoesNoProcessoUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Transactional
    public void execute(UUID processoId, LoteIdsRequest idsAcoesRequest) {
        Processo processo = processoRepository.findById(processoId).orElseThrow(() ->
                new IllegalArgumentException("Processo não encontrado")
        );

        idsAcoesRequest.ids().forEach(processo::removerAcao);

        processoRepository.save(processo);
    }
}
