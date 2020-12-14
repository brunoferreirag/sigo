package br.com.indtextbr.services.sigoapierp.service.util;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateISOConverter extends StdConverter<LocalDate, String> {
    @Override
    public String convert(LocalDate value) {
        return value.toString()+"T00:00:00-03:00";
    }
}
