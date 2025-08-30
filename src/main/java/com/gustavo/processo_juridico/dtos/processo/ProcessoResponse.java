package com.gustavo.processo_juridico.dtos.processo;

import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProcessoResponse(
        UUID id,
        String numero,
        String descricao,
        StatusProcesso status,
        List<Parte> partes,
        List<Acao> acoes
) {
    public record Parte(UUID pessoaId, String pessoaNome, TipoParte tipo) {}
    public record Acao(UUID id, String descricao, LocalDateTime dataRegistro, TipoAcao tipo) {}
}