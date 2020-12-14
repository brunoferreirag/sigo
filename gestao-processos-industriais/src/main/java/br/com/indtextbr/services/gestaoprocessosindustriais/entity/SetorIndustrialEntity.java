package br.com.indtextbr.services.gestaoprocessosindustriais.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_setor_industrial")
public class SetorIndustrialEntity {
	@Id
	@Column(name = "id", updatable = false, insertable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "nome", length = 100, updatable = true, insertable = true)
	private String nome;
	
	@JoinColumn(name = "id_setor_industrial")
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AtividadeSetorIndustrialEntity> atividades;
	
	public SetorIndustrialEntity() {
		this.atividades = new ArrayList<>();
	}
}
