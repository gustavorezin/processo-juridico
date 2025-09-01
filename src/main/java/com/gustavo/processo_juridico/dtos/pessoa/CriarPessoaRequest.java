package com.gustavo.processo_juridico.dtos.pessoa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CriarPessoaRequest(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp="\\d{11}|\\d{14}", message="CPF/CNPJ deve conter 11 ou 14 dígitos")
        String cpfcnpj,
        @NotBlank
        @Email(message="E-mail inválido")
        String email,
        @NotBlank
        @Pattern(regexp="\\d{10,11}", message="Telefone deve conter apenas dígitos (10-11 caracteres)")
        String telefone
) {}
