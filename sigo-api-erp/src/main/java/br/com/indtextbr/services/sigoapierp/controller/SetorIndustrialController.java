package br.com.indtextbr.services.sigoapierp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.indtextbr.services.sigoapierp.model.SetorIndustrial;
import br.com.indtextbr.services.sigoapierp.service.SetorIndustrialService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("setores-industriais")
public class SetorIndustrialController {
	private SetorIndustrialService setorIndustrialService;

	@Autowired
	public SetorIndustrialController(SetorIndustrialService setorIndustrialService) {
		this.setorIndustrialService = setorIndustrialService;
	}

	@RequestMapping(produces = { "application/json" })
	public ResponseEntity<List<SetorIndustrial>> getAllSetoresIndustriais() {
		List<SetorIndustrial> setores = setorIndustrialService.getAllSetoresIndustriais();
		return new ResponseEntity<>(setores, (setores.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<SetorIndustrial> getSetorIndustrialById(@PathVariable int id) {
		SetorIndustrial setor = setorIndustrialService.getSetorIndustrialById(id);
		return new ResponseEntity<>(setor, (setor == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<Void> excluirSetorIndustrial(@PathVariable int id) {
		boolean resultado = setorIndustrialService.excluirSetorIndustrial(id);
		return (resultado) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
	}
	
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<Void> incluirSetorIndustrial(@Valid @RequestBody SetorIndustrial setor) {
		boolean resultado = setorIndustrialService.incluirSetorIndustrial(setor);
		return (resultado) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@PutMapping(value="/{id}" , produces = { "application/json" })
	public ResponseEntity<Void> atualizarSetorIndustrial(@PathVariable int id, @Valid @RequestBody SetorIndustrial setor) {
		setor.setId(id);
		boolean resultado = setorIndustrialService.atualizarSetorIndustrial(setor);
		return (resultado) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}
