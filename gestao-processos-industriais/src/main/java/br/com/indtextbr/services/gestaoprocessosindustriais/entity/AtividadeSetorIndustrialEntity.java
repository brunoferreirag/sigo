package br.com.indtextbr.services.gestaoprocessosindustriais.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tbl_setor_atividade_industrial")
public class AtividadeSetorIndustrialEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, insertable = false)
	private int Id;
	
	@Column(name = "nome",length=150, updatable = true, insertable = true)
	private String nome;
	@Column(name = "executa_segunda",length=1, updatable = true, insertable = true)
	private String executaSegunda;
	@Column(name = "executa_terca",length=1, updatable = true, insertable = true)
	private String executaTerca;
	@Column(name = "executa_quarta",length=1, updatable = true, insertable = true)
	private String executaQuarta;
	@Column(name = "executa_quinta",length=1, updatable = true, insertable = true)
	private String executaQuinta;
	@Column(name = "executa_sexta",length=1, updatable = true, insertable = true)
	private String executaSexta;
	@Column(name = "executa_sabado",length=1, updatable = true, insertable = true)
	private String executaSabado;
	@Column(name = "executa_domingo",length=1, updatable = true, insertable = true)
	private String executaDomingo;
	
	@ManyToOne
	@JoinColumn(name = "id_setor_industrial")
    private SetorIndustrialEntity setorIndustrial;
}
