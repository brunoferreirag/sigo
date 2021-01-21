package br.com.indtextbr.services.logistica.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum EnumAcaoEscritaDB {

    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EnumAcaoEscritaDB fromValue(String value) throws Exception {
        for (EnumAcaoEscritaDB e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new Exception("Ação inválida");
    }

    @JsonValue
    @Override
    public String toString(){
        return this.value;
    }
}
