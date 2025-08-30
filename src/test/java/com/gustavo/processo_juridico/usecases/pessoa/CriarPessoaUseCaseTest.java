package com.gustavo.processo_juridico.usecases.pessoa;

import com.gustavo.processo_juridico.dtos.pessoa.CriarPessoaRequest;
import com.gustavo.processo_juridico.dtos.pessoa.PessoaResponse;
import com.gustavo.processo_juridico.entities.Pessoa;
import com.gustavo.processo_juridico.repositories.PessoaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CriarPessoaUseCaseTest {

    @Mock
    PessoaRepository repository;

    @InjectMocks
    CriarPessoaUseCase criarPessoaUseCase;


    @Test
    @DisplayName("Deve criar pessoa quando CPF/CNPJ não existe")
    void deveCriarPessoa() {
        CriarPessoaRequest pessoaRequest = new CriarPessoaRequest(
                "Teste", "111", "teste@email.com", "999"
        );

        given(repository.findByCpfcnpj("111")).willReturn(Optional.empty());
        given(repository.save(any(Pessoa.class))).willAnswer(invocation -> invocation.getArgument(0));

        PessoaResponse pessoaResponse = criarPessoaUseCase.execute(pessoaRequest);

        assertThat(pessoaResponse).isNotNull();
        assertThat(pessoaResponse.nome()).isEqualTo("Teste");
        assertThat(pessoaResponse.cpfcnpj()).isEqualTo("111");

        ArgumentCaptor<Pessoa> captor = ArgumentCaptor.forClass(Pessoa.class);
        verify(repository).save(captor.capture());
        Pessoa pessoaCaptor = captor.getValue();
        assertThat(pessoaCaptor.getNome()).isEqualTo("Teste");
        assertThat(pessoaCaptor.getCpfcnpj()).isEqualTo("111");
        assertThat(pessoaCaptor.getEmail()).isEqualTo("teste@email.com");
        assertThat(pessoaCaptor.getTelefone()).isEqualTo("999");
    }

    @Test
    @DisplayName("Não deve criar pessoa quando CPF/CNPJ já existe")
    void naoDeveCriarPessoaDuplicada() {
        CriarPessoaRequest pessoaRequest = new CriarPessoaRequest(
                "Teste", "111", "teste@email.com", "999"
        );

        given(repository.findByCpfcnpj("111")).willReturn(Optional.of(
                new Pessoa("Existe", "111", "a@a.com", "999"))
        );

        assertThatThrownBy(() -> criarPessoaUseCase.execute(pessoaRequest))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repository, never()).save(any());
    }
}
