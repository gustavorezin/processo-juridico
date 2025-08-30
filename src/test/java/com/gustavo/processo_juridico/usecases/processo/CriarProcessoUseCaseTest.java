package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.CriarProcessoRequest;
import com.gustavo.processo_juridico.dtos.processo.ProcessoResponse;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CriarProcessoUseCaseTest {

    @Mock
    PessoaRepository pessoaRepository;
    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    CriarProcessoUseCase criarProcessoUseCase;

    @Test
    @DisplayName("Deve criar processo com partes e ações")
    void deveCriarProcesso() {
        UUID idAutor = UUID.randomUUID();
        UUID idReu = UUID.randomUUID();

        CriarProcessoRequest processoRequest = new CriarProcessoRequest(
                "0001",
                "Processo",
                List.of(
                        new CriarProcessoRequest.Parte(idAutor, TipoParte.AUTOR),
                        new CriarProcessoRequest.Parte(idReu, TipoParte.REU)
                ),
                List.of(
                        new CriarProcessoRequest.Acao("Ação 1", LocalDateTime.now(), TipoAcao.PETICAO),
                        new CriarProcessoRequest.Acao("Ação 2", null, TipoAcao.AUDIENCIA)
                )
        );

        given(processoRepository.findByNumero("0001")).willReturn(Optional.empty());
        given(pessoaRepository.findById(idAutor)).willReturn(Optional.of(
                new Pessoa("Autor", "111", "a@a.com", "999"))
        );
        given(pessoaRepository.findById(idReu)).willReturn(Optional.of(
                new Pessoa("Reu", "222", "a@a.com", "999"))
        );

        given(processoRepository.save(any(Processo.class)))
                .willAnswer(inv -> inv.getArgument(0));

        ProcessoResponse processoResponse = criarProcessoUseCase.execute(processoRequest);

        assertThat(processoResponse).isNotNull();
        assertThat(processoResponse.numero()).isEqualTo("0001");
        assertThat(processoResponse.partes()).hasSize(2);
        assertThat(processoResponse.acoes()).hasSize(2);

        ArgumentCaptor<Processo> captor = ArgumentCaptor.forClass(Processo.class);
        verify(processoRepository).save(captor.capture());
        Processo processoCaptor = captor.getValue();
        assertThat(processoCaptor.getNumero()).isEqualTo("0001");
        assertThat(processoCaptor.getPartes()).hasSize(2);
        assertThat(processoCaptor.getAcoes()).hasSize(2);
    }

    @Test
    @DisplayName("Não deve criar se número já existir")
    void naoDeveCriarNumeroDuplicado() {
        CriarProcessoRequest processoRequest = new CriarProcessoRequest("0001", "Processo", List.of(), List.of());
        given(processoRepository.findByNumero("0001"))
                .willReturn(Optional.of(new Processo("0001", "Processo")));

        assertThatThrownBy(() -> criarProcessoUseCase.execute(processoRequest))
                .isInstanceOf(IllegalArgumentException.class);

        verify(processoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve criar se pessoa não existir")
    void naoDeveCriarPessoaInexistente() {
        UUID idInexistente = UUID.randomUUID();
        CriarProcessoRequest processoRequest = new CriarProcessoRequest(
                "0001", "Processo",
                List.of(new CriarProcessoRequest.Parte(idInexistente, TipoParte.AUTOR)),
                null
        );

        given(processoRepository.findByNumero("0001")).willReturn(Optional.empty());
        given(pessoaRepository.findById(idInexistente)).willReturn(Optional.empty());

        assertThatThrownBy(() -> criarProcessoUseCase.execute(processoRequest))
                .isInstanceOf(IllegalArgumentException.class);

        verify(processoRepository, never()).save(any());
    }
}
