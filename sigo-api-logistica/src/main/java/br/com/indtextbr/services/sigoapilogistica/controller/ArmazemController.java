package br.com.indtextbr.services.sigoapilogistica.controller;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.indtextbr.services.sigoapilogistica.model.Armazem;
import br.com.indtextbr.services.sigoapilogistica.service.ArmazemService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("armazens")
public class ArmazemController {
	private ArmazemService armazemService;
	
	@Autowired
	public ArmazemController(ArmazemService armazemService) {
		this.armazemService = armazemService;
	}
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<Armazem>> getArmazens() throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		var armazens = this.armazemService.getAllArmazens();
		return new ResponseEntity<>(armazens, (armazens.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = { "application/json" })
	public ResponseEntity<Armazem> getArmazemById(@PathVariable(value="id") String id) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		var armazem = this.armazemService.getArmazemById(id);
		return new ResponseEntity<>(armazem, (armazem == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<Object> incluirAmazem(@RequestBody @Valid Armazem armazem) throws JsonProcessingException{
		this.armazemService.incluirArmazem(armazem);
		return ResponseEntity.accepted().build();
	}
	
	@PutMapping(value="/{id}",produces = { "application/json" })
	public ResponseEntity<Void> atualizarAmazem(@PathVariable(value="id") String id, @RequestBody @Valid Armazem armazem) throws JsonProcessingException{
		armazem.setId(id);
		this.armazemService.editarArmazem(armazem);
		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping(value="/{id}",produces = { "application/json" })
	public ResponseEntity<Void> inativarArmazem(@PathVariable(value="/{id}") String id) throws JsonProcessingException{
		this.armazemService.inativarArmazem(id);
		return ResponseEntity.accepted().build();
	}
}
