package br.com.indtextbr.services.gestaonormasindustriais.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public enum FormatoConteudo {

    PDF("00"),
    IMG("01"),
    LINK("02");
	@JsonValue
	private String value;

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static FormatoConteudo fromValue(String value) {
		if (value != null) {
			for (FormatoConteudo e : values()) {
				if (e.value.equalsIgnoreCase(value)) {
					return e;
				}
			}
		}
		throw new IllegalArgumentException();
	}
}
