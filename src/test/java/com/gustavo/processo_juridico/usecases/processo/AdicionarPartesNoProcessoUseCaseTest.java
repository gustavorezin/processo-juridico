package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.AdicionarPartesRequest;
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
public class AdicionarPartesNoProcessoUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @Mock
    PessoaRepository pessoaRepository;

    @InjectMocks
    AdicionarPartesNoProcessoUseCase adicionarPartesNoProcessoUseCase;

    @Test
    @DisplayName("Deve adicionar partes e salvar processo")
    void deveAdicionarPartes() {
        UUID processoId = UUID.randomUUID();
        var pessoaId1 = UUID.randomUUID();
        var pessoaId2 = UUID.randomUUID();
        var processo = new Processo("0001", "Processo");

        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));
        given(pessoaRepository.findById(pessoaId1)).willReturn(Optional.of(new Pessoa("A", "111", "a@a.com", "999")));
        given(pessoaRepository.findById(pessoaId2)).willReturn(Optional.of(new Pessoa("B", "222", "a@a.com", "999")));
        given(processoRepository.save(processo)).willAnswer(inv -> inv.getArgument(0));

        AdicionarPartesRequest partesRequest = new AdicionarPartesRequest(List.of(
                new AdicionarPartesRequest.Parte(pessoaId1, TipoParte.AUTOR),
                new AdicionarPartesRequest.Parte(pessoaId2, TipoParte.REU)
        ));

        adicionarPartesNoProcessoUseCase.execute(processoId, partesRequest);

        assertThat(processo.getPartes()).hasSize(2);
        then(processoRepository).should().save(processo);
    }

    @Test
    @DisplayName("Deve lançar erro quando pessoa não existe")
    void deveLancarErroSePessoaInexistente() {
        UUID processoId = UUID.randomUUID();
        UUID pessoaId = UUID.randomUUID();
        Processo processo = new Processo("0001", "Processo");
        given(processoRepository.findById(processoId)).willReturn(Optional.of(processo));
        given(pessoaRepository.findById(pessoaId)).willReturn(Optional.empty());

        AdicionarPartesRequest partesRequest = new AdicionarPartesRequest(List.of(
                new AdicionarPartesRequest.Parte(pessoaId, TipoParte.AUTOR)
        ));

        assertThatThrownBy(() -> adicionarPartesNoProcessoUseCase.execute(processoId, partesRequest))
                .isInstanceOf(IllegalArgumentException.class);

        then(processoRepository).should(never()).save(any());
        assertThat(processo.getPartes()).isEmpty();
    }
}
