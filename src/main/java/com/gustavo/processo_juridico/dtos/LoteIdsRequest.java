package com.gustavo.processo_juridico.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record LoteIdsRequest(
        @NotEmpty
        List<UUID> ids
) {}
