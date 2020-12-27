package br.com.indtextbr.services.gestaonormasindustriais.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.indtextbr.services.gestaonormasindustriais.common.FormatoConteudo;
import br.com.indtextbr.services.gestaonormasindustriais.common.Status;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
@JsonRootName("norma-industrial")
public class NormaIndustrial {

	@Id
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("codigo")
	private String codigo;

	@LastModifiedDate
	@JsonProperty("data-alteracao")
	private Instant dataAlteracao;

	@CreatedDate
	@JsonProperty("data-criacao")
	private Instant dataCriacao;

	@CreatedDate
	@JsonProperty("vigencia")
	private Instant vigencia;

	
	@LastModifiedBy
	@JsonProperty("username-alteracao")
	private String userNameAlteracao;

	@CreatedBy
	@JsonProperty("username-criacao")	
	private String userNameCriacao;
	
	@JsonProperty("versao")
	private String versao;
	
	@JsonProperty("titulo")
	private String titulo;
	
	@JsonProperty("autor")
	private String autor;
	
	@JsonProperty("formato")
	private FormatoConteudo formato;
	
	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("conteudo")
	private String conteudo;
}
