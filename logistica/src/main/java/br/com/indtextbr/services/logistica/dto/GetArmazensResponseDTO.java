package br.com.indtextbr.services.logistica.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetArmazensResponseDTO {
	private List<ArmazemDTO> armazens;
	private Long total;

}
