package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.AdicionarPartesRequest;
import com.gustavo.processo_juridico.entities.ParteProcesso;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdicionarPartesNoProcessoUseCase {
    private final ProcessoRepository processoRepository;
    private final PessoaRepository pessoaRepository;

    public AdicionarPartesNoProcessoUseCase(ProcessoRepository processoRepository, PessoaRepository pessoaRepository) {
        this.processoRepository = processoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public void execute(UUID processoId, AdicionarPartesRequest partesRequest) {
        Processo processo = processoRepository.findById(processoId).orElseThrow(() ->
                new IllegalArgumentException("Processo não encontrado")
        );

        partesRequest.partes().forEach(p -> {
            Pessoa pessoa = pessoaRepository.findById(p.pessoaId()).orElseThrow(
                    () -> new IllegalArgumentException("Pessoa não encontrada: " + p.pessoaId())
            );
            processo.adicionarParte(new ParteProcesso(pessoa, p.tipo()));
        });

        processoRepository.save(processo);
    }
}
