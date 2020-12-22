package br.com.indtextbr.services.logistica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtextbr.services.logistica.common.Constants;
import br.com.indtextbr.services.logistica.dto.ArmazemDTO;
import br.com.indtextbr.services.logistica.dto.ArmazemIDDTO;
import br.com.indtextbr.services.logistica.entity.Armazem;
import br.com.indtextbr.services.logistica.repository.ArmazemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@Service
public class ArmazemService {
	
	private ObjectMapper mapper;
	private ArmazemRepository armazemRepository;
	
	public ArmazemService(ObjectMapper mapper, ArmazemRepository armazemRepository) {
		this.mapper = mapper;
		this.armazemRepository = armazemRepository;
	}
	
	@KafkaListener(topics = "${spring.kafka.armazem-inclusao.topico}", groupId = "${spring.kafka.consumer.group-id}")
	public void incluirArmazem(String payload) throws Exception {
		ArmazemDTO armazemDTO = this.mapper.readValue(payload, ArmazemDTO.class);
		Armazem armazem = new Armazem();
		preencherArmazemEntityDeUmArmazemDTO(armazemDTO,armazem);
		this.armazemRepository.saveAndFlush(armazem);
	}
	
	@KafkaListener(topics = "${spring.kafka.armazem-edicao.topico}", groupId = "${spring.kafka.consumer.group-id}")
	public void editarArmazem(String payload) throws Exception {
		
		ArmazemDTO armazemDTO = this.mapper.readValue(payload, ArmazemDTO.class);
		Optional<Armazem> armazemOptional = this.armazemRepository.findById(armazemDTO.getId());
		
		if(armazemOptional.isPresent()) {
			Armazem armazem = armazemOptional.get();
			preencherArmazemEntityDeUmArmazemDTO(armazemDTO, armazem);
			this.armazemRepository.saveAndFlush(armazem);
		}
		
		
	}
	
	@KafkaListener(topics = "${spring.kafka.armazem-exclusao.topico}", groupId = "${spring.kafka.consumer.group-id}")
	public void inativarArmazem(String payload) throws JsonProcessingException {
		ArmazemIDDTO armazemIDDTO = this.mapper.readValue(payload, ArmazemIDDTO.class);
		Optional<Armazem> armazemOptional = this.armazemRepository.findById(armazemIDDTO.getId());
		if(armazemOptional.isPresent()) {
			Armazem armazem = armazemOptional.get();
			armazem.setStatus(Constants.STATUS_INATIVO);
			this.armazemRepository.saveAndFlush(armazem);
		}
	}
	
	@KafkaListener(topics = "${spring.kafka.armazem-get-all.request.topico}", groupId = "${spring.kafka.consumer.group-id}")
	@SendTo
	public String getAllArmazens() throws JsonProcessingException{
		var armazensEntity = this.armazemRepository.findAllByStatus(Constants.STATUS_ATIVO);
		
		List<ArmazemDTO> armazens = new ArrayList<>();
		
		armazensEntity.forEach(armazem ->{
			ArmazemDTO dto = criarArmazemDTODeUmArmazemEntity(armazem);
			armazens.add(dto);
		});
		
		return this.mapper.writeValueAsString(armazens);
	}
	
	@KafkaListener(topics = "${spring.kafka.armazem-get-by-id.request.topico}", groupId = "${spring.kafka.consumer.group-id}")
	@SendTo
	public String getById(String payload) throws JsonProcessingException{
		var armazemIdDto = this.mapper.readValue(payload,  ArmazemIDDTO.class);
		var armazemEntity = this.armazemRepository.findByIdAndStatus(armazemIdDto.getId(),Constants.STATUS_ATIVO);
		if(armazemEntity !=null) {
			return this.mapper.writeValueAsString(criarArmazemDTODeUmArmazemEntity(armazemEntity));
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
