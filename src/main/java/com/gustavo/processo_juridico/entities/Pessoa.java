package com.gustavo.processo_juridico.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String cpfcnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, String cpfcnpj, String email, String telefone) {
        this.nome = nome;
        this.cpfcnpj = cpfcnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
