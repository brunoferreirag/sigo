package br.com.indtextbr.services.gestaonormasindustriais.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public enum FormatoConteudo {

	PDF, LINK;
}
