package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoParte;

import java.util.List;
import java.util.UUID;

public record AdicionarPartesRequest(List<Parte> partes) {
    public record Parte(UUID pessoaId, TipoParte tipo) {}
}
