package br.com.indtextbr.services.gestaonormasindustriais.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro implements Serializable {
    private String field;
    private String value;
    private String message;

    public Erro(String message) {
        this.message = message;
    }
}
