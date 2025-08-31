package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CriarProcessoRequest(
        @NotBlank()
        String numero,
        @NotBlank()
        String descricao,
        @NotEmpty()
        @Valid
        List<Parte> partes,
        @Valid
        List<Acao> acoes
) {
    public record Parte(
            @NotNull()
            UUID pessoaId,
            @NotNull()
            TipoParte tipo
    ) {}

    public record Acao(
            @NotBlank()
            String descricao,
            LocalDateTime dataRegistro,
            @NotNull()
            TipoAcao tipo
    ) {}
}
