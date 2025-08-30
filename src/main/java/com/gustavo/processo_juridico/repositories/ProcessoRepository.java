package com.gustavo.processo_juridico.repositories;

import com.gustavo.processo_juridico.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, UUID> {
    Optional<Processo> findByNumero(String numero);
}
