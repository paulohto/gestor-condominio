-- Cria o banco de dados
CREATE DATABASE IF NOT EXISTS tb_unidades;

-- Conecta ao banco de dados
\c tb_unidades;

-- Cria a tabela tb_unidades
CREATE TABLE IF NOT EXISTS tb_unidades (
    id BIGSERIAL PRIMARY KEY,
    -- unidade INT NOT NULL,
    unidade_endereco VARCHAR(20) NOT NULL,
    bloco VARCHAR(50),
    --
    status_ocupado INT DEFAULT 0,
    aceita_pets INT DEFAULT 0,
    quantidade_vagas INT DEFAULT 0,
    vagas VARCHAR(50),
    --
    nome_residente VARCHAR(255) NOT NULL,
    --
    img VARCHAR(255),
    condicao_do_residente INT DEFAULT 0,
    --
    telefone VARCHAR(20) NOT NULL,
    whatsapp VARCHAR(20),
    email VARCHAR(255) NOT NULL,
    nascimento DATE NOT NULL,
    morador_desde DATE NOT NULL,
    quantidade_moradores INT DEFAULT 0,
    quantidade_veiculos INT DEFAULT 0,
    quantidade_pets INT DEFAULT 0,

    -- Colunas adicionais podem ser removidas se não forem necessárias
    -- residencial_id BIGINT,
    -- residencial_nome VARCHAR(255),

    -- Define unidade_endereco como UNIQUE para poder referenciá-la
    CONSTRAINT unique_unidade_endereco UNIQUE (unidade_endereco),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cria a tabela tb_dependentes
CREATE TABLE IF NOT EXISTS tb_dependentes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    vinculo VARCHAR(50),
    nascimento DATE,
    telefone VARCHAR(20),
    email VARCHAR(255),
    -- aleterado para consultar pelo número da unidade
    unidade_endereco VARCHAR(20) NOT NULL,
    FOREIGN KEY (unidade_endereco) REFERENCES tb_unidades(unidade_endereco) ON DELETE CASCADE

    --unidade_id BIGINT NOT NULL,
    --FOREIGN KEY (unidade_id) REFERENCES tb_unidades(id) ON DELETE CASCADE
);

-- Cria a tabela tb_veiculos
CREATE TABLE IF NOT EXISTS tb_veiculos (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(50),
    marca VARCHAR(50),
    modelo VARCHAR(50),
    cor VARCHAR(50),
    placa VARCHAR(7) NOT NULL,

    -- aleterado para consultar pelo número da unidade
    unidade_endereco VARCHAR(20) NOT NULL,
    FOREIGN KEY (unidade_endereco) REFERENCES tb_unidades(unidade_endereco) ON DELETE CASCADE

    --unidade_id BIGINT NOT NULL,
    --FOREIGN KEY (unidade_id) REFERENCES tb_unidades(id) ON DELETE CASCADE
);

-- Cria a tabela tb_pets
CREATE TABLE IF NOT EXISTS tb_pets (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(50),
    raca VARCHAR(50),

    -- aleterado para consultar pelo número da unidade
    unidade_endereco VARCHAR(20) NOT NULL,
    FOREIGN KEY (unidade_endereco) REFERENCES tb_unidades(unidade_endereco) ON DELETE CASCADE
   -- unidade_id BIGINT NOT NULL,
   -- FOREIGN KEY (unidade_id) REFERENCES tb_unidades(id) ON DELETE CASCADE
);
