package com.gustavo.processo_juridico.usecases.processo;


import com.gustavo.processo_juridico.dtos.processo.PartesRequest;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class RemoverPartesNoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    RemoverPartesNoProcessoUseCase removerPartesNoProcessoUseCase;

    @Test
    @DisplayName("Deve remover partes e salvar processo")
    void deveRemoverPartes() {
        UUID processoId = UUID.randomUUID();
        UUID pessoaId1 = UUID.randomUUID();
        UUID pessoaId2 = UUID.randomUUID();

        Processo processo = Mockito.spy(new Processo("0001", "Processo"));
        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));

        Mockito.doNothing().when(processo).removerParte(pessoaId1, TipoParte.AUTOR);
        Mockito.doNothing().when(processo).removerParte(pessoaId2, TipoParte.REU);

        PartesRequest partesRequest = new PartesRequest(List.of(
                new PartesRequest.Parte(pessoaId1, TipoParte.AUTOR),
                new PartesRequest.Parte(pessoaId2, TipoParte.REU)
        ));

        removerPartesNoProcessoUseCase.execute(processoId, partesRequest);
        then(processo).should().removerParte(pessoaId1, TipoParte.AUTOR);
        then(processo).should().removerParte(pessoaId2, TipoParte.REU);
        then(processoRepository).should().save(processo);
    }
}
