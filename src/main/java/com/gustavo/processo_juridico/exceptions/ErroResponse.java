package com.gustavo.processo_juridico.exceptions;

import java.time.OffsetDateTime;

public record ErroResponse(
        OffsetDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String path
) {
    public static ErroResponse of(int status, String erro, String mensagem, String path) {
        return new ErroResponse(OffsetDateTime.now(), status, erro, mensagem, path);
    }
}