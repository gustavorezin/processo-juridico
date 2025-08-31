package com.gustavo.processo_juridico.domain;

import com.gustavo.processo_juridico.entities.AcaoProcesso;
import com.gustavo.processo_juridico.entities.ParteProcesso;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProcessoTest {

    private Processo processo;

    private Pessoa pessoa() {
        return new Pessoa("Teste", "111", "teste@email.com", "4899");
    }

    @BeforeEach
    public void setUp() {
        processo = new Processo("0001", "Processo");
    }

    @Test
    void deveAdicionarParteESetarProcesso() {

        ParteProcesso parte = new ParteProcesso(pessoa(), TipoParte.AUTOR);

        processo.adicionarParte(parte);

        assertThat(processo.getPartes()).hasSize(1);
        assertThat(parte.getProcesso()).isSameAs(processo);
    }

    @Test
    void naoDeveAdicionarParteDuplicadaPorCpfcnpjETipo() {
        processo.adicionarParte(new ParteProcesso(pessoa(), TipoParte.AUTOR));

        assertThatThrownBy(() -> processo.adicionarParte(new ParteProcesso(pessoa(), TipoParte.AUTOR)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(processo.getPartes()).hasSize(1);
    }

    @Test
    void deveRegistrarAcaoESetarProcesso() {
        AcaoProcesso acao = new AcaoProcesso("Ação", LocalDateTime.now(), TipoAcao.PETICAO);

        processo.registrarAcao(acao);

        assertThat(processo.getAcoes()).hasSize(1);
        assertThat(acao.getProcesso()).isSameAs(processo);
    }

    @Test
    void deveLancarErroAoRemoverPartesComProcessoInativo() {
        processo.suspender();
        assertThatThrownBy(() -> processo.removerParte(UUID.randomUUID(), TipoParte.AUTOR))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Processo suspenso ou arquivado");
    }

    @Test
    void deveLancarErroAoRemoverPartesComPessoaNaoEncontrada() {
        assertThatThrownBy(() -> processo.removerParte(UUID.randomUUID(), TipoParte.AUTOR))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parte não encontrada");
    }

    @Test
    void deveLancarErroAoRemoverAcoesComProcessoInativo() {
        processo.suspender();
        assertThatThrownBy(() -> processo.removerAcao(UUID.randomUUID()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Processo suspenso ou arquivado");
    }

    @Test
    void deveLancarErroAoRemoverAcoesNaoEncontradas() {
        assertThatThrownBy(() -> processo.removerAcao(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Ação não encontrada");
    }

}
