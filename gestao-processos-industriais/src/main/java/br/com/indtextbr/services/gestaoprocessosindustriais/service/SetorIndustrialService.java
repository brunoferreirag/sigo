package br.com.indtextbr.services.gestaoprocessosindustriais.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.indtextbr.services.gestaoprocessosindustriais.entity.AtividadeIndustrial;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.AtividadeSetorIndustrialEntity;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.SetorIndustrial;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.SetorIndustrialEntity;
import br.com.indtextbr.services.gestaoprocessosindustriais.repository.SetorIndustrialRepository;

@Service
public class SetorIndustrialService {
	private SetorIndustrialRepository repository;

	@Autowired
	public SetorIndustrialService(SetorIndustrialRepository setorIndustrialRepository) {
		this.repository = setorIndustrialRepository;
	}

	public List<SetorIndustrial> getAllSetorIndustrial() {
		var resultadosIntermediarios = this.repository.findAll();
		List<SetorIndustrial> setoresIndustriais = new ArrayList<>();

		resultadosIntermediarios.forEach(p -> {
			setoresIndustriais.add(this.converteSetorIndustrialEntityParaWs(p));
		});

		return setoresIndustriais;
	}

	private SetorIndustrial converteSetorIndustrialEntityParaWs(SetorIndustrialEntity p) {
		SetorIndustrial setorIndustrial = new SetorIndustrial();
		setorIndustrial.setId(p.getId());
		setorIndustrial.setNome(p.getNome());
		if (!p.getAtividades().isEmpty()) {
			p.getAtividades().forEach(q -> {
				AtividadeIndustrial atividadeIndustrial = new AtividadeIndustrial();
				atividadeIndustrial.setCodigoSetorIndustrial(p.getId());
				atividadeIndustrial.setId(q.getId());
				atividadeIndustrial.setNome(q.getNome());
				atividadeIndustrial.setExecutaDomingo(q.getExecutaDomingo());
				atividadeIndustrial.setExecutaQuarta(q.getExecutaQuarta());
				atividadeIndustrial.setExecutaQuinta(q.getExecutaQuinta());
				atividadeIndustrial.setExecutaSabado(q.getExecutaSabado());
				atividadeIndustrial.setExecutaSegunda(q.getExecutaSegunda());
				atividadeIndustrial.setExecutaSexta(q.getExecutaSexta());
				atividadeIndustrial.setExecutaTerca(q.getExecutaTerca());
				setorIndustrial.getListaAtividades().add(atividadeIndustrial);

			});
		}
		return setorIndustrial;
	}

	public SetorIndustrial getSetorIndustrialPorId(int id) {
		var resultadoIntermediario = this.repository.findById(id);
		if (resultadoIntermediario.isPresent()) {
			return this.converteSetorIndustrialEntityParaWs(resultadoIntermediario.get());
		}
		return null;
	}

	public SetorIndustrialEntity incluirSetorIndustrial(SetorIndustrial setorIndustrial) {
		SetorIndustrialEntity setorEntity = ConverteDeSetorIndustrialEntityParaWs(setorIndustrial);
		var setorIndustrialResponse = this.repository.saveAndFlush(setorEntity);
		return setorIndustrialResponse;
	}

	private SetorIndustrialEntity ConverteDeSetorIndustrialEntityParaWs(SetorIndustrial setorIndustrial) {
		SetorIndustrialEntity setorEntity = new SetorIndustrialEntity();
		setorEntity.setNome(setorIndustrial.getNome());
		setorEntity.setId(setorIndustrial.getId());
		setorIndustrial.getListaAtividades().forEach(p ->{
			AtividadeSetorIndustrialEntity atividade = new AtividadeSetorIndustrialEntity();
			
			atividade.setSetorIndustrial(setorEntity);
			atividade.setExecutaDomingo(p.getExecutaDomingo());
			atividade.setNome(p.getNome());
			atividade.setExecutaSabado(p.getExecutaSabado());
			atividade.setExecutaSexta(p.getExecutaSexta());
			atividade.setExecutaQuinta(p.getExecutaQuinta());
			atividade.setExecutaQuarta(p.getExecutaQuarta());
			atividade.setExecutaTerca(p.getExecutaTerca());
			atividade.setExecutaSegunda(p.getExecutaSegunda());
			setorEntity.getAtividades().add(atividade);
		});
		return setorEntity;
	}

	public SetorIndustrialEntity atualizarSetorIndustrial(SetorIndustrialEntity setorIndustrialRequest) {
		Optional<SetorIndustrialEntity> setorIndustrialResult = this.repository
				.findById(setorIndustrialRequest.getId());
		if (setorIndustrialResult.isPresent()) {
			SetorIndustrialEntity setorIndustrial = setorIndustrialResult.get();
			setorIndustrial.setNome(setorIndustrialRequest.getNome());
			setorIndustrial.setAtividades(setorIndustrialRequest.getAtividades());
			return this.repository.saveAndFlush(setorIndustrial);
		}
		return null;
	}

	public void excluirSetorIndustrial(int id) {
		this.repository.deleteById(id);
	}

}
