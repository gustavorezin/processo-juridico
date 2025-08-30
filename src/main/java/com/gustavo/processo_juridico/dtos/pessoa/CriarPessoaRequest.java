package com.gustavo.processo_juridico.dtos.pessoa;

public record CriarPessoaRequest(
        String nome,
        String cpfcnpj,
        String email,
        String telefone
) {}
