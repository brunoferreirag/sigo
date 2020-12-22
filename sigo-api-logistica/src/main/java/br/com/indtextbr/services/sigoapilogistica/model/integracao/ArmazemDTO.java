package br.com.indtextbr.services.sigoapilogistica.model.integracao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArmazemDTO {
	private String Id;

	private String endereco;
	
	private String bairro;
	
	private String cidadeEstado;
	
	private String CEP;
	
	private Boolean armazenaItemsParaVenda;
	
	private Boolean armazenaItemsParaCompra;
	
	private String status;
}
