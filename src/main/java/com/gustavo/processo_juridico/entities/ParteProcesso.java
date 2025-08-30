package com.gustavo.processo_juridico.entities;

import com.gustavo.processo_juridico.entities.enums.TipoParte;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"processo_id","pessoa_id","tipo"}))
public class ParteProcesso {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParte tipo;

    public ParteProcesso() {
    }

    public ParteProcesso(Pessoa pessoa, TipoParte tipo) {
        this.pessoa = pessoa;
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public TipoParte getTipo() {
        return tipo;
    }
}
