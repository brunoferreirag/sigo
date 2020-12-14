DROP TABLE IF EXISTS tbl_setor_industrial;
  
CREATE TABLE tbl_setor_industrial (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(100) NOT NULL
);

CREATE TABLE tbl_setor_atividade_industrial (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  id_setor_industrial INT,
  nome VARCHAR(100) NOT NULL,
  executa_segunda VARCHAR(1) NOT NULL,
  executa_terca VARCHAR(1) NOT NULL,
  executa_quarta VARCHAR(1) NOT NULL,
  executa_quinta VARCHAR(1) NOT NULL,
  executa_sexta VARCHAR(1) NOT NULL,
  executa_sabado VARCHAR(1) NOT NULL,
  executa_domingo VARCHAR(1) NOT NULL
);

 ALTER TABLE tbl_setor_atividade_industrial
    ADD FOREIGN KEY (id_setor_industrial) 
    REFERENCES tbl_setor_industrial(id);