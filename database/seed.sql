-- Dados para demonstração

USE triagemdb;

-- =========================================================
-- Funcionários
-- 1 administrador e 1 atendente comum
-- =========================================================

INSERT INTO funcionarios (matricula_funcionario, nome_funcionario) VALUES
(50, 'Marcela Castro'),
(67, 'Paulina Moreira');

INSERT INTO funcionario_login (id_login, fk_matricula, senha, admin) VALUES
(1, 50, '1234', 1),
(16, 67, '1234', 0);

-- =========================================================
-- Pacientes
-- Apenas os necessários para exemplo
-- =========================================================

INSERT INTO pacientes (id_paciente, nomePaciente, cpf, idade, convenio) VALUES
(42, 'José Oliveira', '123458', 30, 'Particular'),
(44, 'Warley Silveira', '33123123', 33, 'Particular'),
(45, 'Laura Pereira', '3242424235', 32, 'Particular'),
(48, 'Regina Lopes', '654867867', 33, 'Unimed'),
(49, 'Renato Nogueira', '324532432', 32, 'Particular');

-- =========================================================
-- Sintomas
-- Mantidos todos os sintomas cadastrados no projeto
-- =========================================================

INSERT INTO sintomas (id_sintoma, descricao, classificacao) VALUES
(1, 'fc acima de 150 bpm', 'VERMELHA'),
(2, 'dor no peito intensa', 'VERMELHA'),
(3, 'falta de ar intensa', 'VERMELHA'),
(4, 'desmaio', 'VERMELHA'),
(5, 'confusão mental', 'VERMELHA'),
(6, 'convulsão', 'VERMELHA'),
(7, 'sangramento intenso', 'VERMELHA'),
(8, 'trauma na cabeça', 'VERMELHA'),
(9, 'baleado', 'VERMELHA'),
(10, 'queimadura grave', 'VERMELHA'),
(11, 'sinais de avc', 'VERMELHA'),
(12, 'perda de força de um lado', 'VERMELHA'),
(13, 'dificuldade para falar', 'VERMELHA'),
(14, 'reação alérgica grave', 'VERMELHA'),
(15, 'dor atrás dos olhos', 'AMARELA'),
(16, 'dor de cabeça intensa', 'AMARELA'),
(17, 'tosse com sangue', 'AMARELA'),
(18, 'febre alta', 'AMARELA'),
(19, 'vômitos persistentes', 'AMARELA'),
(20, 'dor abdominal intensa', 'AMARELA'),
(21, 'dor forte ao urinar com febre', 'AMARELA'),
(22, 'ansiedade com agressividade', 'AMARELA'),
(23, 'crise de pânico intensa', 'AMARELA'),
(24, 'dor moderada no peito', 'AMARELA'),
(25, 'falta de ar moderada', 'AMARELA'),
(26, 'sangramento moderado', 'AMARELA'),
(27, 'dor de cabeça leve', 'VERDE'),
(28, 'espirros leves', 'VERDE'),
(29, 'tosse leve', 'VERDE'),
(30, 'dor de garganta leve', 'VERDE'),
(31, 'febre baixa', 'VERDE'),
(32, 'náusea leve', 'VERDE'),
(33, 'dor muscular leve', 'VERDE'),
(34, 'ansiedade que pode esperar atendimento', 'VERDE'),
(35, 'corte pequeno', 'VERDE'),
(36, 'irritação na pele', 'VERDE');

-- =========================================================
-- Atendimentos
-- =========================================================

INSERT INTO atendimentos
(id_atendimento, fk_id_paciente, emergencia, classificacao, fk_matricula, data_hora, status)
VALUES
(39, 42, 1, 'VERMELHA', 50, '2026-09-15 15:26:36', 'AGUARDANDO'),
(41, 44, 0, 'VERDE',    50, '2026-09-15 15:45:34', 'AGUARDANDO'),
(42, 45, 0, 'AMARELA',  50, '2026-09-18 10:44:33', 'FINALIZADO'),
(45, 48, 0, 'AMARELA',  67, '2026-09-18 10:47:56', 'AGUARDANDO'),
(46, 49, 1, 'VERMELHA', 50, '2026-09-18 11:50:50', 'AGUARDANDO');

-- =========================================================
-- Relação atendimento x sintomas
-- =========================================================

INSERT INTO atendimento_sintoma (fk_id_atendimento, fk_id_sintoma) VALUES
(39, 5),
(39, 8),
(39, 9),
(39, 28),

(41, 31),
(41, 36),

(42, 18),
(42, 25),

(45, 21),
(45, 29),
(45, 32),

(46, 6),
(46, 27);
