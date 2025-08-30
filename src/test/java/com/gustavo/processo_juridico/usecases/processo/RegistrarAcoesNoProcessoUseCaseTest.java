package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.RegistrarAcoesRequest;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.DisplayName;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class RegistrarAcoesNoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    RegistrarAcoesNoProcessoUseCase registrarAcoesNoProcessoUseCase;

    @Test
    @DisplayName("Deve registrar todas as ações e salvar o processo")
    void deveRegistrarAcoes() {
        UUID id = UUID.randomUUID();
        Processo processo = new Processo("0001", "Processo");
        given(processoRepository.findById(id)).willReturn(Optional.of(processo));
        given(processoRepository.save(processo)).willAnswer(inv -> inv.getArgument(0));

        RegistrarAcoesRequest acoesRequest = new RegistrarAcoesRequest(List.of(
                new RegistrarAcoesRequest.Acao("Ação 1", LocalDateTime.now(), TipoAcao.PETICAO),
                new RegistrarAcoesRequest.Acao("Ação 2", null, TipoAcao.AUDIENCIA)
        ));

        registrarAcoesNoProcessoUseCase.execute(id, acoesRequest);

        assertThat(processo.getAcoes()).hasSize(2);
        assertThat(processo.getAcoes().get(0).getDescricao()).isEqualTo("Ação 1");
        assertThat(processo.getAcoes().get(1).getDescricao()).isEqualTo("Ação 2");
        assertThat(processo.getAcoes().get(1).getDataRegistro()).isNotNull();

        then(processoRepository).should().save(processo);
    }

    @Test
    @DisplayName("Deve lançar erro quando processo não encontrado")
    void deveLancarErroSeProcessoInexistente() {
        UUID id = UUID.randomUUID();
        given(processoRepository.findById(id)).willReturn(Optional.empty());

        RegistrarAcoesRequest acoesRequest = new RegistrarAcoesRequest(List.of(
                new RegistrarAcoesRequest.Acao("Ação", LocalDateTime.now(), TipoAcao.PETICAO)
        ));

        assertThatThrownBy(() -> registrarAcoesNoProcessoUseCase.execute(id, acoesRequest))
                .isInstanceOf(IllegalArgumentException.class);

        then(processoRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("Deve respeitar regra de domínio de não registrar em processo inativo")
    void naoDeveRegistrarSeProcessoInativo() {
        UUID id = UUID.randomUUID();
        Processo processo = new Processo("0001", "Processo");
        processo.suspender();
        given(processoRepository.findById(id)).willReturn(Optional.of(processo));

        RegistrarAcoesRequest acoesRequest = new RegistrarAcoesRequest(List.of(
                new RegistrarAcoesRequest.Acao("Ação", LocalDateTime.now(), TipoAcao.PETICAO)
        ));

        assertThatThrownBy(() -> registrarAcoesNoProcessoUseCase.execute(id, acoesRequest))
                .isInstanceOf(IllegalStateException.class);

        then(processoRepository).should(never()).save(any());
        assertThat(processo.getAcoes()).isEmpty();
    }
}
