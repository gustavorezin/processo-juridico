package com.gustavo.processo_juridico.dtos.pessoa;

import java.util.UUID;

public record PessoaResponse(
        UUID id,
        String nome,
        String cpfcnpj,
        String email,
        String telefone
) {}
