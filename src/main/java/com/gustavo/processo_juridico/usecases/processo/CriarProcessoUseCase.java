package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.CriarProcessoRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.ParteProcesso;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CriarProcessoUseCase {
    private final ProcessoRepository processoRepository;
    private final PessoaRepository pessoaRepository;

    public CriarProcessoUseCase(ProcessoRepository processoRepository, PessoaRepository pessoaRepository) {
        this.processoRepository = processoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ProcessoResponse execute(CriarProcessoRequest processoRequest) {
        processoRepository.findByNumero(processoRequest.numero()).ifPresent(p -> {
            throw new IllegalArgumentException("Já existe processo com esse número");
        });

        Processo processo = new Processo(processoRequest.numero(), processoRequest.descricao());

        processoRequest.partes().forEach(p -> {
            Pessoa pessoa = pessoaRepository.findById(p.pessoaId()).orElseThrow(
                    () -> new IllegalArgumentException("Pessoa não encontrada: " + p.pessoaId())
            );
            ParteProcesso parte = new ParteProcesso(pessoa, p.tipo());
            processo.adicionarParte(parte);
        });

        if (processoRequest.acoes() != null) {
            processoRequest.acoes().forEach(a -> {
                processo.registrarAcao(new AcaoProcesso(a.descricao(), a.dataRegistro(), a.tipo()));
            });
        }

        Processo novoProcesso = processoRepository.save(processo);

        List<ProcessoResponse.Parte> partes = novoProcesso.getPartes().stream()
                .map(pp -> new ProcessoResponse.Parte(
                        pp.getPessoa().getId(),
                        pp.getPessoa().getNome(),
                        pp.getTipo()
                ))
                .toList();

        List<ProcessoResponse.Acao> acoes = novoProcesso.getAcoes().stream()
                .map(a -> new ProcessoResponse.Acao(
                        a.getId(),
                        a.getDescricao(),
                        a.getDataRegistro(),
                        a.getTipo()
                ))
                .toList();

        return new ProcessoResponse(
                novoProcesso.getId(),
                novoProcesso.getNumero(),
                novoProcesso.getDescricao(),
                novoProcesso.getStatus(),
                partes,
                acoes
        );
    }
}
