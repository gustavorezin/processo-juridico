package com.gustavo.processo_juridico.entities;

import com.gustavo.processo_juridico.entities.enums.TipoAcao;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AcaoProcesso {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcao tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;

    public AcaoProcesso() {
    }

    public AcaoProcesso(String descricao, LocalDateTime dataRegistro, TipoAcao tipo) {
        this.descricao = descricao;
        this.dataRegistro = dataRegistro != null ? dataRegistro : LocalDateTime.now();
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public TipoAcao getTipo() {
        return tipo;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}
