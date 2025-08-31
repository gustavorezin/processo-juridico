package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListarPartesDoProcessoUseCase {
    private final ProcessoRepository processoRepository;

    public ListarPartesDoProcessoUseCase(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public List<ProcessoResponse.Parte> execute(UUID processoId) {
        Processo p = processoRepository.findById(processoId).orElseThrow(
                () -> new IllegalArgumentException("Processo não encontrado")
        );
        return p.getPartes().stream()
                .map(pp -> new ProcessoResponse.Parte(
                        pp.getPessoa().getId(),
                        pp.getPessoa().getNome(),
                        pp.getTipo())
                )
                .toList();
    }
}
