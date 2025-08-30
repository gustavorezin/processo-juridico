package com.gustavo.processo_juridico.repositories;

import com.gustavo.processo_juridico.entities.Pessoa;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PessoaRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void deveBuscarPessoaPorCpfcnpj() {
        Pessoa pessoa = new Pessoa("Teste", "111", "teste@email.com", "999");
        this.entityManager.persist(pessoa);

        Optional<Pessoa> pessoaEncontrada = this.pessoaRepository.findByCpfcnpj("111");

        assertThat(pessoaEncontrada.isPresent()).isTrue();
    }

    @Test
    void naoDeveBuscarPessoaPorCpfcnpjQuandoPessoaNaoExiste() {
        Optional<Pessoa> pessoaEncontrada = this.pessoaRepository.findByCpfcnpj("111");

        assertThat(pessoaEncontrada.isEmpty()).isTrue();
    }
}
