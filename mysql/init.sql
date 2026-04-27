CREATE DATABASE IF NOT EXISTS financasdb;

USE financasdb;

CREATE TABLE IF NOT EXISTS despesas (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        descricao VARCHAR(255),
    categoria VARCHAR(100),
    valor DOUBLE NOT NULL,
    data DATE
    );

INSERT INTO despesas (descricao, categoria, valor, data) VALUES
                                                             ('Mercado do mês', 'Alimentação', 450.75, '2026-04-01'),
                                                             ('Conta de luz', 'Moradia', 180.30, '2026-04-05'),
                                                             ('Internet fibra', 'Serviços', 99.90, '2026-04-10'),
                                                             ('Transporte faculdade', 'Transporte', 120.00, '2026-04-15');