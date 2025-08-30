package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoAcao;

import java.time.LocalDateTime;
import java.util.List;

public record RegistrarAcoesRequest(
        List<Acao> acoes
) {
    public record Acao(String descricao, LocalDateTime dataRegistro, TipoAcao tipo) {}
}
