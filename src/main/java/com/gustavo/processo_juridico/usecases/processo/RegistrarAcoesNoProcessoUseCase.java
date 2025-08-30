package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrarAcoesNoProcessoUseCase {
    private final ProcessoRepository processoRepository;

    public RegistrarAcoesNoProcessoUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Transactional
    public void execute(UUID processoId, RegistrarAcoesRequest acoesRequest) {
        Processo processo = processoRepository.findById(processoId).orElseThrow(() ->
                new IllegalArgumentException("Processo não encontrado")
        );

        acoesRequest.acoes().forEach(a -> {
            processo.registrarAcao(new AcaoProcesso(a.descricao(), a.dataRegistro(), a.tipo()));
        });

        processoRepository.save(processo);
    }
}
