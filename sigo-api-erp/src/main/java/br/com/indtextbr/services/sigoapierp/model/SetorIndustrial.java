package br.com.indtextbr.services.sigoapierp.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SetorIndustrial {
	@JsonProperty("id")
	private int Id;

	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("atividades")
	private List<AtividadeSetorIndustrial> atividades;
	
	public SetorIndustrial() {
		this.atividades = new ArrayList<>();
	}
	
}
