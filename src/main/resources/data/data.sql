-- Criação da tabela tb_residencial
CREATE TABLE IF NOT EXISTS tb_residencial (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    lazer TEXT[], -- PostgreSQL array type for the List<String> field
    valor_condominio DECIMAL(10, 2),
    elevador BOOLEAN,
    empresa_portaria VARCHAR(255),
    empresa_zeladoria VARCHAR(255),
    empresa_vigilancia VARCHAR(255),
    empresa_boletos VARCHAR(255),
    quantidade_unidades INT,
    quantidade_publico INT,
    quantidade_unidades_utilizam_app INT,
    quantidade_unidades_com_pet INT,
    quantidade_unidades_com_veiculo INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp for last update time
);

-- Adiciona índice na coluna "nome" para melhorar a performance de consultas
CREATE INDEX idx_nome ON tb_residencial (nome);

-- Reinicia a sequência para a tabela tb_residencial
ALTER SEQUENCE tb_residencial_id_seq RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM tb_residencial);

-- Criação da tabela tb_lazer
CREATE TABLE IF NOT EXISTS tb_lazer (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    descricao VARCHAR(255) NOT NULL
);

-- Adiciona índice na coluna "descricao" para melhorar a performance de consultas
CREATE INDEX idx_descricao ON tb_lazer (descricao);

-- Reinicia a sequência para a tabela tb_lazer
ALTER SEQUENCE tb_lazer_id_seq RESTART WITH (SELECT COALESCE(MAX(id), 0) + 1 FROM tb_lazer);

-- Criação da tabela de associação residencial_lazer
CREATE TABLE IF NOT EXISTS residencial_lazer (
    residencial_id BIGINT REFERENCES tb_residencial(id),
    lazer_id BIGINT REFERENCES tb_lazer(id),
    PRIMARY KEY (residencial_id, lazer_id)
);

-- Adiciona índice nas colunas "residencial_id" e "lazer_id" para melhorar a performance de consultas
CREATE INDEX idx_residencial_id ON residencial_lazer (residencial_id);
CREATE INDEX idx_lazer_id ON residencial_lazer (lazer_id);

-- Reinicia a sequência para a tabela residencial_lazer
ALTER SEQUENCE residencial_lazer_residencial_id_seq RESTART WITH (SELECT COALESCE(MAX(residencial_id), 0) + 1 FROM residencial_lazer);
ALTER SEQUENCE residencial_lazer_lazer_id_seq RESTART WITH (SELECT COALESCE(MAX(lazer_id), 0) + 1 FROM residencial_lazer);
