package com.gustavo.processo_juridico.usecases.pessoa;

import com.gustavo.processo_juridico.dtos.pessoa.CriarPessoaRequest;
import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarPessoaUseCase {
    private final PessoaRepository repository;

    public CriarPessoaUseCase(PessoaRepository repository) {
        this.repository = repository;
    }

    public PessoaResponse execute(CriarPessoaRequest pessoaRequest) {
        repository.findByCpfcnpj(pessoaRequest.cpfcnpj()).ifPresent(p -> {
            throw new IllegalArgumentException("Pessoa com este CPF/CNPJ já cadastrado");
        });

        Pessoa novaPessoa = repository.save(
                new Pessoa(pessoaRequest.nome(), pessoaRequest.cpfcnpj(), pessoaRequest.email(), pessoaRequest.telefone())
        );

        return new PessoaResponse(
                novaPessoa.getId(),
                novaPessoa.getNome(),
                novaPessoa.getCpfcnpj(),
                novaPessoa.getEmail(),
                novaPessoa.getTelefone()
        );
    }
}
