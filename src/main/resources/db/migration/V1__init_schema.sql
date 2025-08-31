CREATE TABLE acao_processo
(
    id            UUID         NOT NULL,
    descricao     VARCHAR(255) NOT NULL,
    data_registro TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    tipo          VARCHAR(255) NOT NULL,
    processo_id   UUID         NOT NULL,
    CONSTRAINT pk_acaoprocesso PRIMARY KEY (id)
);

CREATE TABLE parte_processo
(
    id          UUID         NOT NULL,
    processo_id UUID         NOT NULL,
    pessoa_id   UUID         NOT NULL,
    tipo        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_parteprocesso PRIMARY KEY (id)
);

CREATE TABLE pessoa
(
    id       UUID         NOT NULL,
    nome     VARCHAR(255) NOT NULL,
    cpfcnpj  VARCHAR(20)  NOT NULL,
    email    VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    CONSTRAINT pk_pessoa PRIMARY KEY (id)
);

CREATE TABLE processo
(
    id            UUID         NOT NULL,
    numero        VARCHAR(255) NOT NULL,
    descricao     VARCHAR(255) NOT NULL,
    status        VARCHAR(255) NOT NULL,
    data_abertura date         NOT NULL,
    CONSTRAINT pk_processo PRIMARY KEY (id)
);

ALTER TABLE parte_processo
    ADD CONSTRAINT uc_13ed143243be3f2800725de06 UNIQUE (processo_id, pessoa_id, tipo);

ALTER TABLE pessoa
    ADD CONSTRAINT uc_pessoa_cpfcnpj UNIQUE (cpfcnpj);

ALTER TABLE processo
    ADD CONSTRAINT uc_processo_numero UNIQUE (numero);

ALTER TABLE acao_processo
    ADD CONSTRAINT FK_ACAOPROCESSO_ON_PROCESSO FOREIGN KEY (processo_id) REFERENCES processo (id);

ALTER TABLE parte_processo
    ADD CONSTRAINT FK_PARTEPROCESSO_ON_PESSOA FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE parte_processo
    ADD CONSTRAINT FK_PARTEPROCESSO_ON_PROCESSO FOREIGN KEY (processo_id) REFERENCES processo (id);