package br.com.indtextbr.services.logistica.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_armazem")
public class Armazem {
	@Id
	@Column(name = "id", length = 10, updatable = false, insertable = false)
	private String id;

	@Column(name = "endereco", length = 150, updatable = true, insertable = true)
	private String endereco;
	
	@Column(name = "cidade_estado", length = 100, updatable = true, insertable = true)
	private String cidadeEstado;
	
	@Column(name = "bairro", length = 100, updatable = true, insertable = true)
	private String bairro;
	
	
	@Column(name = "cep", length = 8, updatable = true, insertable = true)
	private String CEP;
	
	@Column(name = "armazena_venda",updatable = true, insertable = true)
	private Boolean armazenaItemsParaVenda;
	
	@Column(name = "armazena_compra",updatable = true, insertable = true)
	private Boolean armazenaItemsParaCompra;
	
	@Column(name = "status", length = 1, updatable = true, insertable = true)
	private String status;
}
