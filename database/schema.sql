-- Estrutura simplificada do banco de dados. Utilize pra criar um banco local.

CREATE DATABASE IF NOT EXISTS triagemdb
    DEFAULT CHARACTER SET utf8mb4;

USE triagemdb;

-- =========================================================
-- Funcionários
-- =========================================================

CREATE TABLE funcionarios (
    matricula_funcionario INT NOT NULL AUTO_INCREMENT,
    nome_funcionario VARCHAR(100) NOT NULL,
    PRIMARY KEY (matricula_funcionario)
);

-- =========================================================
-- Pacientes
-- =========================================================

CREATE TABLE pacientes (
    id_paciente INT NOT NULL AUTO_INCREMENT,
    nomePaciente VARCHAR(45),
    cpf VARCHAR(11),
    idade INT,
    convenio VARCHAR(45),
    PRIMARY KEY (id_paciente)
);

-- =========================================================
-- Sintomas
-- =========================================================

CREATE TABLE sintomas (
    id_sintoma INT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(100) NOT NULL,
    classificacao ENUM('VERDE', 'AMARELA', 'VERMELHA') NOT NULL,
    PRIMARY KEY (id_sintoma),
    UNIQUE (descricao)
);

-- =========================================================
-- Login dos funcionários
-- =========================================================

CREATE TABLE funcionario_login (
    id_login INT NOT NULL AUTO_INCREMENT,
    fk_matricula INT NOT NULL,
    senha VARCHAR(20) NOT NULL,
    admin TINYINT(1) NOT NULL,
    PRIMARY KEY (id_login),
    CONSTRAINT fk_funcionario_login
        FOREIGN KEY (fk_matricula)
        REFERENCES funcionarios (matricula_funcionario)
);

-- =========================================================
-- Atendimentos
-- =========================================================

CREATE TABLE atendimentos (
    id_atendimento INT NOT NULL AUTO_INCREMENT,
    fk_id_paciente INT NOT NULL,
    emergencia TINYINT(1) NOT NULL,
    classificacao VARCHAR(20) NOT NULL,
    fk_matricula INT NOT NULL,
    data_hora DATETIME,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_atendimento),

    CONSTRAINT fk_atendimento_paciente
        FOREIGN KEY (fk_id_paciente)
        REFERENCES pacientes (id_paciente),

    CONSTRAINT fk_atendimento_funcionario
        FOREIGN KEY (fk_matricula)
        REFERENCES funcionarios (matricula_funcionario)
);

-- =========================================================
-- Relação entre atendimentos e sintomas
-- =========================================================

CREATE TABLE atendimento_sintoma (
    fk_id_atendimento INT NOT NULL,
    fk_id_sintoma INT NOT NULL,

    PRIMARY KEY (fk_id_atendimento, fk_id_sintoma),

    CONSTRAINT fk_ats_atendimento
        FOREIGN KEY (fk_id_atendimento)
        REFERENCES atendimentos (id_atendimento),

    CONSTRAINT fk_ats_sintoma
        FOREIGN KEY (fk_id_sintoma)
        REFERENCES sintomas (id_sintoma)
);


