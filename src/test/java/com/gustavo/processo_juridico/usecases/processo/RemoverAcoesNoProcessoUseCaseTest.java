package com.gustavo.processo_juridico.usecases.processo;


import com.gustavo.processo_juridico.dtos.LoteIdsRequest;
import com.gustavo.processo_juridico.dtos.processo.PartesRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class RemoverAcoesNoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    RemoverAcoesNoProcessoUseCase removerAcoesNoProcessoUseCase;

    @Test
    @DisplayName("Deve remover acoes e salvar processo")
    void deveRemoverAcoes() {
        UUID processoId = UUID.randomUUID();
        UUID acaoId1 = UUID.randomUUID();
        UUID acaoId2 = UUID.randomUUID();

        Processo processo = Mockito.spy(new Processo("0001", "Processo"));
        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));

        Mockito.doNothing().when(processo).removerAcao(acaoId1);
        Mockito.doNothing().when(processo).removerAcao(acaoId2);

        LoteIdsRequest idsAcoesRequest = new LoteIdsRequest(List.of(acaoId1, acaoId2));

        removerAcoesNoProcessoUseCase.execute(processoId, idsAcoesRequest);
        then(processo).should().removerAcao(acaoId1);
        then(processo).should().removerAcao(acaoId2);
        then(processoRepository).should().save(processo);
    }
}
