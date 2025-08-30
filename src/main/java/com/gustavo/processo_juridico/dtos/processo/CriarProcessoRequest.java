package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CriarProcessoRequest(
        String numero,
        String descricao,
        List<Parte> partes,
        List<Acao> acoes
) {
    public record Parte(UUID pessoaId, TipoParte tipo) {};
    public record Acao(String descricao, LocalDateTime dataRegistro, TipoAcao tipo) {};
}
