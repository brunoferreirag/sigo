package br.com.indtextbr.services.gestaonormasindustriais.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.indtextbr.services.gestaonormasindustriais.model.Erro;
import lombok.extern.log4j.Log4j2;


@ControllerAdvice
@Log4j2
public class ExceptionHandlerController {
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<List<Erro>> handlerInternalServerErrorException(RuntimeException ex) {
        log.error("internal server error", ex);
        List<Erro> erros = Collections.singletonList(new Erro("Erro interno no servidor"));
        return new ResponseEntity<>(erros, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
