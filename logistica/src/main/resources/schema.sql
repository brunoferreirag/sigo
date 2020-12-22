DROP TABLE IF EXISTS tbl_armazem;
  
CREATE TABLE tbl_armazem (
  id varchar(20) PRIMARY KEY,
  endereco VARCHAR(150) NOT NULL,
  bairro VARCHAR(100) NOT NULL,
  cidade_estado VARCHAR(100) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  armazena_venda BOOLEAN NOT NULL,
  armazena_compra BOOLEAN NOT NULL,
  status VARCHAR(1) NOT NULL
);
