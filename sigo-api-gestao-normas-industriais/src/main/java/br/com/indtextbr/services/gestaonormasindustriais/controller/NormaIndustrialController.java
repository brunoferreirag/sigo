package br.com.indtextbr.services.gestaonormasindustriais.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.indtextbr.services.gestaonormasindustriais.model.NormaIndustrial;
import br.com.indtextbr.services.gestaonormasindustriais.service.NormaIndustrialService;

@RestController
@RequestMapping("norma-industrial")
@CrossOrigin(origins = "*")
public class NormaIndustrialController {
	private NormaIndustrialService service;
	
	@Autowired
	public NormaIndustrialController(NormaIndustrialService service) {
		this.service = service;
	}
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<NormaIndustrial>> getAllNormasIndustriaisAtivas() throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		var normaisIndustriais = this.service.getAllNormasIndustriaisEmVigor();
		return new ResponseEntity<>(normaisIndustriais, (normaisIndustriais.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = { "application/json" })
	public ResponseEntity<NormaIndustrial> getNormaIndustrialById(@PathVariable(value="id") String id) {
		var normaIndustrial = this.service.getById(id);
		return new ResponseEntity<>(normaIndustrial, (normaIndustrial == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<Object> incluirNormaIndustrial(@RequestBody @Valid NormaIndustrial normaIndustrial){
		var normaIndustrialIncluida = this.service.criarNormaIndustrial(normaIndustrial);
		URI location = URI.create(String.format("/%s", normaIndustrialIncluida.getId()));
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value="/{id}",produces = { "application/json" })
	public ResponseEntity<NormaIndustrial> editarNormaIndustrial(@PathVariable(value="id") String id, @RequestBody @Valid NormaIndustrial normaIndustrial) {
		normaIndustrial.setId(id);
		NormaIndustrial normaIndustrialEditada = this.service.editarNormaIndustrial(normaIndustrial);
		return new ResponseEntity<>(normaIndustrialEditada, (normaIndustrialEditada == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}",produces = { "application/json" })
	public ResponseEntity<Void> inativarNormaIndustrial(@PathVariable(value="id") String id) {
		this.service.inativarNormaIndustrial(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="busca",produces = { "application/json" })
	public ResponseEntity<List<NormaIndustrial>> pesquisarNormasIndustriais(@RequestParam(value="codigo")String codigo, @RequestParam(value="titulo")String titulo, @RequestParam(value="versao")String versao, @RequestParam(value="autor")String autor, @RequestParam(value="conteudo")String conteudo) {
		var normaisIndustriais = this.service.pesquisarNormasIndustriais(codigo, titulo, versao, autor, conteudo);
		return new ResponseEntity<>(normaisIndustriais, (normaisIndustriais.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

}
