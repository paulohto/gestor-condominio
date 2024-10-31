-- Cria o banco de dados
CREATE DATABASE IF NOT EXISTS tb_sindico;

-- Conecta ao banco de dados
\c tb_sindico;

-- Cria a tabela tb_sindico
CREATE TABLE IF NOT EXISTS tb_sindico (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    -- id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    nascimento DATE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    whatsapp VARCHAR(20),
    email VARCHAR(255) NOT NULL,
    cep VARCHAR(9),
    endereco VARCHAR(255)
    --residencial TEXT[], -- PostgreSQL array type for the List<String> field
        -- MS-RESIDENCIAL
         residencial_id BIGINT,
         residencial_nome VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp for last update time
);

-- Adiciona índice na coluna "nome" para melhorar a performance de consultas
CREATE INDEX idx_nome ON tb_sindico (nome);