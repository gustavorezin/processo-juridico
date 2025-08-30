package com.gustavo.processo_juridico.entities;

import com.gustavo.processo_juridico.entities.enums.StatusProcesso;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Processo {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProcesso status;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcaoProcesso> acoes = new ArrayList<>();

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParteProcesso> partes = new ArrayList<>();

    public Processo() {
    }

    public Processo(String numero, String descricao) {
        this.numero = numero;
        this.descricao = descricao;
        this.dataAbertura = LocalDate.now();
        this.status = StatusProcesso.ATIVO;
    }

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusProcesso getStatus() {
        return status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public List<AcaoProcesso> getAcoes() {
        return acoes;
    }

    public List<ParteProcesso> getPartes() {
        return partes;
    }
}
