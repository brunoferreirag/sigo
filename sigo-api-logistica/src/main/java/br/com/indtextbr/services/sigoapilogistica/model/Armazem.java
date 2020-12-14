package br.com.indtextbr.services.sigoapilogistica.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Armazem {
	@NotBlank(message = "Identificador do armazém é obrigatório")
	private String id;
	@NotBlank(message = "Endereço é obrigatório")
	private String endereco;
	@NotBlank(message = "Cidade/Estado é obrigatório")
	private String cidadeEstado;
	@NotBlank(message = "CEP é obrigatório")
	private String CEP;
	@NotNull(message = "Não pode ser nulo")
	private Boolean armazenaItemsParaVenda;
	@NotNull(message = "Não pode ser nulo")
	private Boolean armazenaItemsParaCompra;
	
	private String status;

}
