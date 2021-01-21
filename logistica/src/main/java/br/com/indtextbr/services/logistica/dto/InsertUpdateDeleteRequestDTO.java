package br.com.indtextbr.services.logistica.dto;

import br.com.indtextbr.services.logistica.common.EnumAcaoEscritaDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertUpdateDeleteRequestDTO {
	private EnumAcaoEscritaDB acao;
	private ArmazemDTO armazem;

}
