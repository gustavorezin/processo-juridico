package com.gustavo.processo_juridico.usecases.pessoa;

import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarPessoasUseCase {
    private final PessoaRepository pessoaRepository;

    public ListarPessoasUseCase(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<PessoaResponse> execute() {
        return pessoaRepository.findAll().stream().map(p -> new PessoaResponse(
                p.getId(),
                p.getNome(),
                p.getCpfcnpj(),
                p.getEmail(),
                p.getTelefone()
        )).toList();
    }
}
