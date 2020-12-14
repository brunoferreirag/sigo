INSERT INTO tbl_setor_industrial (id,nome) VALUES
  (1,'Fiação Cardada'),
  (2,'Fiação Penteada'),
  (3,'Tecelagem'),
  (4,'Beneficiamento');
  
INSERT INTO tbl_setor_atividade_industrial (
id,
id_setor_industrial,
nome,
executa_segunda,
executa_terca,
executa_quarta,
executa_quinta,
executa_sexta,
executa_sabado,
executa_domingo) 
VALUES
(1,1,'Armazém de pluma/rama/fardos','S','S','S','S','S','S','S'),
(2,1,'Linha de Abertura','S','S','S','S','S','S','S'),
(3,1,'Carda , Cardadeira ou Cardadora','S','S','S','S','S','S','S'),
(4,1,'Laminador, Passador ou Passadeira de 1ª passagem sem regulagem','S','S','S','S','S','S','S'),
(5,1,'Laminador, Passador ou Passadeira de 2ª passagem com regulagem','S','S','S','S','S','S','S'),
(6,1,'Torce ou Maçaroqueira','S','S','S','S','S','S','S'),
(7,1,'Contínuo ou Filatório de Anel','S','S','S','S','S','S','S'),
(8,1,'Bobinadeira ou Conicaleira','S','S','S','S','S','S','S'),
(9,1,'Vaporizadora','S','S','S','S','S','S','S'),
(10,1,'Expedição','S','S','S','S','S','N','N');

INSERT INTO tbl_setor_atividade_industrial (
id,
id_setor_industrial,
nome,
executa_segunda,
executa_terca,
executa_quarta,
executa_quinta,
executa_sexta,
executa_sabado,
executa_domingo) 
VALUES
(11,2,'Armazém de pluma/rama/fardos','S','S','S','S','S','S','S'),
(12,2,'Linha de Abertura','S','S','S','S','S','S','S'),
(13,2,'Carda , Cardadeira ou Cardadora','S','S','S','S','S','S','S'),
(14,2,'Laminador, Passador ou Passadeira de 1ª passagem sem regulagem','S','S','S','S','S','S','S'),
(15,2,'Reunideira ou Juntadeira','S','S','S','S','S','S','S'),
(16,2,'Laminadeira (opcional)','S','S','S','S','S','S','S'),
(17,2,'Penteadeira','S','S','S','S','S','S','S'),
(18,2,'laminador, Passador ou Passadeira de 2ª passagem com regulagem','S','S','S','S','S','S','S'),
(19,2,'Torce ou Maçaroqueira','S','S','S','S','S','S','S'),
(20,2,'Contínuo ou Filatório de Anel','S','S','S','S','S','S','S'),
(21,2,'Bobinadeira ou Conicaleira','S','S','S','S','S','S','S'),
(22,2,'Vaporizadora','S','S','S','S','S','S','N'),
(23,2,'Expedição','S','S','S','S','S','N','N');


INSERT INTO tbl_setor_atividade_industrial (
id,
id_setor_industrial,
nome,
executa_segunda,
executa_terca,
executa_quarta,
executa_quinta,
executa_sexta,
executa_sabado,
executa_domingo) 
VALUES
(24,3,'Abertura da cala','S','S','S','S','S','S','S'),
(25,3,'Inserção da trama','S','S','S','S','S','S','S'),
(26,3,'Batida do pente','S','S','S','S','S','S','S');


INSERT INTO tbl_setor_atividade_industrial (
id,
id_setor_industrial,
nome,
executa_segunda,
executa_terca,
executa_quarta,
executa_quinta,
executa_sexta,
executa_sabado,
executa_domingo) 
VALUES
(27,4,'Preparação','S','S','S','S','S','S','S'),
(28,4,'Tingimento','S','S','S','S','S','S','S'),
(29,4,'Estampagem','S','S','S','S','S','S','S'),
(30,4,'Acabamento','S','S','S','S','S','S','S');

