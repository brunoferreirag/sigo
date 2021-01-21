package br.com.indtextbr.services.sigoapilogistica.model;

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
	private List<Armazem> armazens;
	private Long total;

}
