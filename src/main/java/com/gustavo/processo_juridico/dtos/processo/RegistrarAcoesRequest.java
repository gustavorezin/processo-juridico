package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RegistrarAcoesRequest(
        @NotEmpty()
        @Valid
        List<Acao> acoes
) {
    public record Acao(
            @NotBlank()
            String descricao,
            LocalDateTime dataRegistro,
            @NotNull()
            TipoAcao tipo
    ) {}
}
