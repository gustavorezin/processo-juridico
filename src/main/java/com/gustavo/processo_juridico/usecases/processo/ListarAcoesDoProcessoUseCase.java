package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListarAcoesDoProcessoUseCase {
    private final ProcessoRepository processoRepository;

    public ListarAcoesDoProcessoUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public List<ProcessoResponse.Acao> execute(UUID processoId) {
        Processo p = processoRepository.findById(processoId).orElseThrow(
                () -> new IllegalArgumentException("Processo não encontrado")
        );
        return p.getAcoes().stream()
                .map(a -> new ProcessoResponse.Acao(a.getId(), a.getDescricao(), a.getDataRegistro(), a.getTipo()))
                .toList();
    }
}
