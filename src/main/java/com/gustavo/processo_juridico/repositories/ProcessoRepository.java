package com.gustavo.processo_juridico.repositories;

import com.gustavo.processo_juridico.entities.Processo;
import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, UUID> {
    Optional<Processo> findByNumero(String numero);

    Page<Processo> findByStatus(StatusProcesso status, Pageable pageable);

    Page<Processo> findByDataAberturaBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query("""
        SELECT pr
        FROM
            Processo pr
            JOIN pr.partes p
            JOIN p.pessoa pp
        WHERE pp.cpfcnpj = :cpfcnpj
    """)
    Page<Processo> findByParteCpfcnpj(@Param("cpfcnpj") String cpfcnpj, Pageable pageable);
}
