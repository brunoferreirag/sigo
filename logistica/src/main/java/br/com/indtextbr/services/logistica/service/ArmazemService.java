package br.com.indtextbr.services.logistica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtextbr.services.logistica.common.Constants;
import br.com.indtextbr.services.logistica.dto.ArmazemDTO;
import br.com.indtextbr.services.logistica.dto.GetArmazensRequestDTO;
import br.com.indtextbr.services.logistica.dto.GetArmazensResponseDTO;
import br.com.indtextbr.services.logistica.dto.InsertUpdateDeleteRequestDTO;
import br.com.indtextbr.services.logistica.entity.Armazem;
import br.com.indtextbr.services.logistica.repository.ArmazemRepository;

@Service
public class ArmazemService {

	private ObjectMapper mapper;
	private ArmazemRepository armazemRepository;

	public ArmazemService(ObjectMapper mapper, ArmazemRepository armazemRepository) {
		this.mapper = mapper;
		this.armazemRepository = armazemRepository;
	}

	@KafkaListener(topics = "${spring.kafka.armazem-insert-update-delete.topico}", groupId = "${spring.kafka.consumer.group-id}")
	public void escreverArmazemNaBase(String payload) throws Exception {
		InsertUpdateDeleteRequestDTO dto = this.mapper.readValue(payload, InsertUpdateDeleteRequestDTO.class);
		switch (dto.getAcao()) {
		case INSERT: {
			this.incluirArmazem(dto.getArmazem());
		}
		case UPDATE: {
			this.editarArmazem(dto.getArmazem());
		}
		default: {
			this.inativarArmazem(dto.getArmazem().getId());
		}
		}

	}

	private void incluirArmazem(ArmazemDTO armazemDTO) throws Exception {
		Armazem armazem = new Armazem();
		preencherArmazemEntityDeUmArmazemDTO(armazemDTO, armazem);
		this.armazemRepository.save(armazem);
	}

	public void editarArmazem(ArmazemDTO armazemDTO) throws Exception {

		Optional<Armazem> armazemOptional = this.armazemRepository.findById(armazemDTO.getId());

		if (armazemOptional.isPresent()) {
			Armazem armazem = armazemOptional.get();
			preencherArmazemEntityDeUmArmazemDTO(armazemDTO, armazem);
			this.armazemRepository.save(armazem);
		}

		throw new Exception();

	}

	public void inativarArmazem(String codigo) throws JsonProcessingException {
		Optional<Armazem> armazemOptional = this.armazemRepository.findById(codigo);
		if (armazemOptional.isPresent()) {
			Armazem armazem = armazemOptional.get();
			armazem.setStatus(Constants.STATUS_INATIVO);
			this.armazemRepository.save(armazem);
		}
	}

	@KafkaListener(topics = "${spring.kafka.armazem-read.topico}", groupId = "${spring.kafka.consumer.group-id}")
	@SendTo
	public String lerArmazens(String payload) throws JsonProcessingException {
		GetArmazensRequestDTO dto= mapper.readValue(payload, GetArmazensRequestDTO.class);
		if(dto.getCodigoArmazem() ==null) {
			return this.getAllArmazens(dto);
		}
		return this.getById(dto);
	}
	
	public String getAllArmazens(GetArmazensRequestDTO dto) throws JsonProcessingException {

		PageRequest page = PageRequest.of(dto.getPage(), dto.getSize());

		var armazensEntity = this.armazemRepository.findAllByStatus(Constants.STATUS_ATIVO, page);

		List<ArmazemDTO> armazens = new ArrayList<>();

		armazensEntity.forEach(armazem -> {
			ArmazemDTO armazemDTO = criarArmazemDTODeUmArmazemEntity(armazem);
			armazens.add(armazemDTO);
		});

		var resposta = new GetArmazensResponseDTO();
		resposta.setArmazens(armazens);
		resposta.setTotal(armazensEntity.getTotalElements());

		return this.mapper.writeValueAsString(resposta);
	}

	public String getById(GetArmazensRequestDTO dto) throws JsonProcessingException {
		var armazemEntity = this.armazemRepository.findByIdAndStatus(dto.getCodigoArmazem(), Constants.STATUS_ATIVO);
		if (armazemEntity != null) {
			var armazemDTO = criarArmazemDTODeUmArmazemEntity(armazemEntity);
			List<ArmazemDTO> armazens = new ArrayList<>();
			armazens.add(armazemDTO);
			var resposta = new GetArmazensResponseDTO();
			resposta.setArmazens(armazens);
			resposta.setTotal((long)armazens.size());
			this.mapper.writeValueAsString(resposta);
		}
		return null;
	}

	private ArmazemDTO criarArmazemDTODeUmArmazemEntity(Armazem armazem) {
		ArmazemDTO dto = new ArmazemDTO();
		dto.setArmazenaItemsParaCompra(armazem.getArmazenaItemsParaCompra());
		dto.setArmazenaItemsParaVenda(armazem.getArmazenaItemsParaVenda());
		dto.setBairro(armazem.getBairro());
		dto.setCEP(armazem.getCEP());
		dto.setCidadeEstado(armazem.getCidadeEstado());
		dto.setEndereco(armazem.getEndereco());
		dto.setId(armazem.getId());
		dto.setStatus(armazem.getStatus());
		return dto;
	}

	private void preencherArmazemEntityDeUmArmazemDTO(ArmazemDTO armazemDTO, Armazem armazem) {
		armazem.setArmazenaItemsParaCompra(armazemDTO.getArmazenaItemsParaCompra());
		armazem.setArmazenaItemsParaVenda(armazemDTO.getArmazenaItemsParaVenda());
		armazem.setCEP(armazemDTO.getCEP());
		armazem.setCidadeEstado(armazemDTO.getCidadeEstado());
		armazem.setEndereco(armazemDTO.getEndereco());
		armazem.setId(armazemDTO.getId());
		armazem.setStatus(armazemDTO.getStatus());
		armazem.setBairro(armazemDTO.getBairro());
	}

}
