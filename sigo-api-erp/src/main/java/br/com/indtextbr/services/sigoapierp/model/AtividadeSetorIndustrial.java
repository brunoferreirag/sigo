package br.com.indtextbr.services.sigoapierp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeSetorIndustrial {
	@JsonProperty("id")
	private int Id;
	
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("executa-segunda")
	private String executaSegunda;
	
	@JsonProperty("executa-terca")
	private String executaTerca;
	
	@JsonProperty("executa-quarta")
	private String executaQuarta;
	
	@JsonProperty("executa-quinta")
	private String executaQuinta;
	
	@JsonProperty("executa-sexta")
	private String executaSexta;
	
	@JsonProperty("executa-sabado")
	private String executaSabado;
	
	@JsonProperty("executa-domingo")
	private String executaDomingo;
	
}
