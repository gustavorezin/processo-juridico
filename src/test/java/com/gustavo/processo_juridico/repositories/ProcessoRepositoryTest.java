package com.gustavo.processo_juridico.repositories;

import com.gustavo.processo_juridico.entities.ParteProcesso;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.TipoParte;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProcessoRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProcessoRepository processoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void deveBuscarProcessosPorCpfcnpjPaginado() {
        Pessoa pessoa1 = pessoaRepository.save(new Pessoa("Teste 1", "111", "a@a.com", "999"));
        Pessoa pessoa2 = pessoaRepository.save(new Pessoa("Teste 2", "222", "a@a.com", "999"));

        Processo processo1 = new Processo("0001", "Desc 1");
        processo1.adicionarParte(new ParteProcesso(pessoa1, TipoParte.AUTOR));
        Processo processo2 = new Processo("0002", "Desc 2");
        processo2.adicionarParte(new ParteProcesso(pessoa1, TipoParte.REU));
        Processo processo3 = new Processo("0003", "Desc 3");
        processo3.adicionarParte(new ParteProcesso(pessoa2, TipoParte.AUTOR));

        processoRepository.save(processo1);
        processoRepository.save(processo2);
        processoRepository.save(processo3);

        Page<Processo> page = processoRepository.findByParteCpfcnpj("111", PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(2);
        List<String> numeroProcessos = page.map(Processo::getNumero).getContent();
        assertThat(numeroProcessos).containsExactlyInAnyOrder("0001", "0002");
    }

    @Test
    void naoDeveBuscarProcessosPorCpfcnpjQuandoNaoPossuir() {
        Page<Processo> processoPage = processoRepository.findByParteCpfcnpj("111", PageRequest.of(0, 10));
        assertThat(processoPage.getTotalElements()).isEqualTo(0);
        assertThat(processoPage.getContent()).isEmpty();
    }
}
