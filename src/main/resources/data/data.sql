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
    sindico_id BIGINT,
    sindico_nome VARCHAR(255),
    --sindico_telefone VARCHAR(20),
    --sindico_email VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp for last update time
);


-- Criação da tabela tb_lazer
CREATE TABLE IF NOT EXISTS tb_lazer (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_residencial_lazer (
    residencial_id BIGINT NOT NULL,
    lazer_id BIGINT NOT NULL,
    PRIMARY KEY (residencial_id, lazer_id),
    FOREIGN KEY (residencial_id) REFERENCES tb_residencial (id) ON DELETE CASCADE,
    FOREIGN KEY (lazer_id) REFERENCES tb_lazer (id) ON DELETE CASCADE
);
