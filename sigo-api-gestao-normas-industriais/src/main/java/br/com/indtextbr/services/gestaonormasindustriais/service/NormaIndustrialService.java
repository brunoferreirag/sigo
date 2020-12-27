package br.com.indtextbr.services.gestaonormasindustriais.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.indtextbr.services.gestaonormasindustriais.common.Status;
import br.com.indtextbr.services.gestaonormasindustriais.model.NormaIndustrial;
import br.com.indtextbr.services.gestaonormasindustriais.repository.NormaIndustrialRepository;

@Service
public class NormaIndustrialService {
	private NormaIndustrialRepository repository;
	
	public NormaIndustrialService(NormaIndustrialRepository repository) {
		this.repository = repository;
	}
	
	public List<NormaIndustrial> getAllNormasIndustriaisEmVigor(){
		return this.repository.findByStatus(Status.EM_VIGOR);
	}
	
	public NormaIndustrial criarNormaIndustrial(NormaIndustrial normaIndustrial) {
		return this.repository.save(normaIndustrial);
	}
	
	public NormaIndustrial editarNormaIndustrial(NormaIndustrial normaIndustrialEdicao) {
		var normaIndustrialEncontrada = this.repository.findById(normaIndustrialEdicao.getId());
		if(normaIndustrialEncontrada.isPresent()) {
			var normaIndustrial = normaIndustrialEncontrada.get();
			normaIndustrial.setConteudo(normaIndustrialEdicao.getConteudo());
			normaIndustrial.setAutor(normaIndustrialEdicao.getAutor());
			normaIndustrial.setTitulo(normaIndustrialEdicao.getTitulo());
			normaIndustrial.setUserNameAlteracao(normaIndustrialEdicao.getUserNameAlteracao());
			normaIndustrial.setFormato(normaIndustrialEdicao.getFormato());
			normaIndustrial.setVersao(normaIndustrialEdicao.getVersao());
			normaIndustrial.setVigencia(normaIndustrialEdicao.getVigencia());
			return this.repository.save(normaIndustrial);
		}
		return null;
	}
	
	public boolean inativarNormaIndustrial(String id) {
		var normaIndustrialEncontrada = this.repository.findById(id);
		if(normaIndustrialEncontrada.isPresent()) {
			var normaIndustrial = normaIndustrialEncontrada.get();
			normaIndustrial.setStatus(Status.INATIVO);
			this.repository.save(normaIndustrial);
			return true;
		}
		return false;
	}
	
	public NormaIndustrial getById(String id) {
		var normaIndustrialEncontrada = this.repository.findById(id);
		if(normaIndustrialEncontrada.isPresent()) {
			return normaIndustrialEncontrada.get();
		}
		return null;
	}
	
	public List<NormaIndustrial> pesquisarNormasIndustriais(String id, String titulo,String versao, String autor, String conteudo){
		return this.repository.findByIdLikeAndTituloLikeAndVersaoAndAutorLikeAndConteudoLikeAndStatus(id, titulo, versao, autor, conteudo, Status.EM_VIGOR);
	}
}
