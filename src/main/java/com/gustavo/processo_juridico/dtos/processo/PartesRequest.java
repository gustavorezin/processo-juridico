package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoParte;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PartesRequest(
        @NotEmpty()
        @Valid
        List<Parte> partes
) {
    public record Parte(
            @NotNull()
            UUID pessoaId,
            @NotNull()
            TipoParte tipo
    ) {}
}
