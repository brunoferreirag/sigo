package br.com.indtextbr.services.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetArmazensRequestDTO {
	private int size;
	private int page;
	private String codigoArmazem;
}
