package com.gustavo.processo_juridico.usecases.processo;

import com.gustavo.processo_juridico.dtos.processo.ProcessoResumoResponse;
import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.repositories.ProcessoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class BuscarProcessosPorParteUseCaseTest {

    @Mock
    ProcessoRepository processoRepository;

    @InjectMocks
    BuscarProcessosPorParteUseCase buscarProcessosPorParteUseCase;

    @Test
    @DisplayName("Deve listar processos paginados para ProcessoResumoResponse")
    void deveListarProcessosPaginadosPorCpfcnpj() {
        Processo processo1 = new Processo("0001", "Processo 1");
        Processo processo2 = new Processo("0002", "Processo 2");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("numero").ascending());
        PageImpl<Processo> processosPage = new PageImpl<>(List.of(processo1, processo2), pageable, 2);

        given(processoRepository.findByParteCpfcnpj("111", pageable)).willReturn(processosPage);

        Page<ProcessoResumoResponse> resumoResponse = buscarProcessosPorParteUseCase.execute("111", pageable);

        assertThat(resumoResponse.getTotalElements()).isEqualTo(2);
        assertThat(resumoResponse.getContent()).hasSize(2);
        assertThat(resumoResponse.getContent().get(0).numero()).isEqualTo("0001");
        assertThat(resumoResponse.getContent().get(1).numero()).isEqualTo("0002");

        then(processoRepository).should().findByParteCpfcnpj("111", pageable);
    }
}
