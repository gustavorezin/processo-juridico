package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ArquivarProcessosUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    ArquivarProcessosUseCase arquivarProcessosUseCase;

    @Test
    void deveArquivarTodosOsIds() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Processo processo1 = new Processo("0001", "Processo");
        Processo processo2 = new Processo("0002", "Processo");

        given(processoRepository.findById(id1)).willReturn(Optional.of(processo1));
        given(processoRepository.findById(id2)).willReturn(Optional.of(processo2));

        arquivarProcessosUseCase.execute(new LoteIdsRequest(List.of(id1, id2)));

        assertThat(processo1.getStatus()).isEqualTo(StatusProcesso.ARQUIVADO);
        assertThat(processo2.getStatus()).isEqualTo(StatusProcesso.ARQUIVADO);
    }

    @Test
    void deveLancarErroQuandoAlgumIdNaoExiste() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Processo processo1 = new Processo("0001", "Processo");

        given(processoRepository.findById(id1)).willReturn(Optional.of(processo1));
        given(processoRepository.findById(id2)).willReturn(Optional.empty());

        assertThatThrownBy(() -> arquivarProcessosUseCase.execute(new LoteIdsRequest(List.of(id1, id2))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
