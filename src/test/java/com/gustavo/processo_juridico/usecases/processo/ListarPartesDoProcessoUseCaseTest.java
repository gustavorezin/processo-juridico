package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.ParteProcesso;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
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
public class ListarPartesDoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    ListarPartesDoProcessoUseCase listarPartesDoProcessoUseCase;

    @Test
    void deveListarAcoesDoProcesso() {
        UUID processoId = UUID.randomUUID();
        Processo processo = new Processo("0001", "Processo");
        processo.adicionarParte(new ParteProcesso(new Pessoa("Autor", "111","a@a.com","999"), TipoParte.AUTOR));
        processo.adicionarParte(new ParteProcesso(new Pessoa("Reu", "222","a@a.com","999"), TipoParte.REU));

        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));

        List<ProcessoResponse.Parte> partes = listarPartesDoProcessoUseCase.execute(processoId);

        assertThat(partes).hasSize(2);
        assertThat(partes).extracting(ProcessoResponse.Parte::pessoaNome)
                .containsExactlyInAnyOrder("Autor","Reu");
        assertThat(partes).extracting(ProcessoResponse.Parte::tipo)
                .containsExactlyInAnyOrder(TipoParte.AUTOR, TipoParte.REU);
        then(processoRepository).should().findById(processoId);
    }
}
