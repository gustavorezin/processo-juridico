package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class ListarAcoesDoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    ListarAcoesDoProcessoUseCase listarAcoesDoProcessoUseCase;

    @Test
    void deveListarAcoesDoProcesso() {
        UUID processoId = UUID.randomUUID();
        Processo processo = new Processo("0001", "Processo");
        processo.registrarAcao(new AcaoProcesso("Ação 1", LocalDateTime.now(), TipoAcao.PETICAO));
        processo.registrarAcao(new AcaoProcesso("Ação 2", null, TipoAcao.AUDIENCIA));

        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));

        List<ProcessoResponse.Acao> acoes = listarAcoesDoProcessoUseCase.execute(processoId);

        assertThat(acoes).hasSize(2);
        assertThat(acoes.get(0).descricao()).isEqualTo("Ação 1");
        assertThat(acoes.get(0).tipo()).isEqualTo(TipoAcao.PETICAO);
        assertThat(acoes.get(1).descricao()).isEqualTo("Ação 2");
        assertThat(acoes.get(1).dataRegistro()).isNotNull();
        then(processoRepository).should().findById(processoId);
    }
}
