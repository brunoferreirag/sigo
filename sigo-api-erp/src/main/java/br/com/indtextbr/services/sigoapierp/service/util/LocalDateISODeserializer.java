package br.com.indtextbr.services.sigoapierp.service.util;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class LocalDateISODeserializer extends LocalDateDeserializer {

	private static final long serialVersionUID = 8361568447047841196L;

	public LocalDateISODeserializer() {
        super(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
