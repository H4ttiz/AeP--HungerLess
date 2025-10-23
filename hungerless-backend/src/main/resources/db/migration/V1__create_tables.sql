CREATE TABLE enderecos (
    id BIGSERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE TABLE contatos (
    id BIGSERIAL PRIMARY KEY,
    telefone VARCHAR(20),
    celular VARCHAR(20) NOT NULL,
    email_alternativo VARCHAR(255)
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    documento VARCHAR(50) NOT NULL,
    endereco_id BIGINT UNIQUE REFERENCES enderecos(id),
    contato_id BIGINT UNIQUE REFERENCES contatos(id)
);

CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL,
    descricao TEXT
);

CREATE TABLE alimentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    validade DATE NOT NULL,
    quantidade INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    categoria_id BIGINT REFERENCES categorias(id),
    doador_id BIGINT REFERENCES usuarios(id),
    data_cadastro TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE doacoes (
    id BIGSERIAL PRIMARY KEY,
    alimento_id BIGINT REFERENCES alimentos(id),
    doador_id BIGINT REFERENCES usuarios(id),
    receptor_id BIGINT REFERENCES usuarios(id),
    status VARCHAR(50) NOT NULL,
    data_solicitacao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_aprovacao TIMESTAMP WITHOUT TIME ZONE,
    data_entrega TIMESTAMP WITHOUT TIME ZONE
);
