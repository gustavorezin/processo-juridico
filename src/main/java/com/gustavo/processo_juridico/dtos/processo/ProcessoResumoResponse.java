package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.StatusProcesso;

import java.time.LocalDate;
import java.util.UUID;

public record ProcessoResumoResponse(
        UUID id,
        String numero,
        StatusProcesso status,
        LocalDate dataAbertura
) {
}
