package com.gustavo.processo_juridico.dtos.pessoa;

import jakarta.validation.constraints.NotBlank;

public record CriarPessoaRequest(
        @NotBlank
        String nome,
        @NotBlank
        String cpfcnpj,
        @NotBlank
        String email,
        @NotBlank
        String telefone
) {}
