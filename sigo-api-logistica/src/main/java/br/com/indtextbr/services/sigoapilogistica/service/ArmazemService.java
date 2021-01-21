package br.com.indtextbr.services.sigoapilogistica.service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtextbr.services.sigoapilogistica.common.EnumAcaoEscritaDB;
import br.com.indtextbr.services.sigoapilogistica.model.Armazem;
import br.com.indtextbr.services.sigoapilogistica.model.GetArmazensRequestDTO;
import br.com.indtextbr.services.sigoapilogistica.model.GetArmazensResponseDTO;
import br.com.indtextbr.services.sigoapilogistica.model.InsertUpdateDeleteRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Service
public class ArmazemService {

	@Value("${spring.kafka.armazem-insert-update-delete.topico}")
	private String topicoIncluirEditarDeletarArmazem;
	
	@Value("${spring.kafka.armazem-read.topico}")
	private String topicoLerArmazem;

	@Value("${spring.kafka.consumer.group-id}")
	private String grupoKafka;

	private KafkaTemplate<String, String> kafkaTemplate;

	private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

	private ObjectMapper mapper;

	public ArmazemService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper,
			ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.mapper = mapper;
		this.replyingKafkaTemplate = replyingKafkaTemplate;
	}

	public void incluirArmazem(Armazem armazem) throws JsonProcessingException {
		InsertUpdateDeleteRequestDTO dto = new InsertUpdateDeleteRequestDTO();
		dto.setArmazem(armazem);
		dto.setAcao(EnumAcaoEscritaDB.INSERT);
		String payload = this.mapper.writeValueAsString(dto);
		this.kafkaTemplate.send(this.topicoIncluirEditarDeletarArmazem, payload);
	}
	
	public void editarArmazem(Armazem armazem) throws JsonProcessingException {
		InsertUpdateDeleteRequestDTO dto = new InsertUpdateDeleteRequestDTO();
		dto.setArmazem(armazem);
		dto.setAcao(EnumAcaoEscritaDB.UPDATE);
		String payload = this.mapper.writeValueAsString(dto);
		this.kafkaTemplate.send(this.topicoIncluirEditarDeletarArmazem, payload);
	}
	
	public void inativarArmazem(String id) throws JsonProcessingException {
		InsertUpdateDeleteRequestDTO dto = new InsertUpdateDeleteRequestDTO();
		Armazem armazemDTO = new Armazem();
		armazemDTO.setId(id);
		dto.setArmazem(armazemDTO);
		dto.setAcao(EnumAcaoEscritaDB.DELETE);
		
		String payload = this.mapper.writeValueAsString(id);
		this.kafkaTemplate.send(this.topicoIncluirEditarDeletarArmazem, payload);
	}

	public Armazem getArmazemById(String id)
			throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		GetArmazensRequestDTO dto = new GetArmazensRequestDTO();
		dto.setCodigoArmazem(id);
		ProducerRecord<String, String> record = new ProducerRecord<>(this.topicoLerArmazem, null, id, mapper.writeValueAsString(dto));
		RequestReplyFuture<String, String, String> future = this.replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> response = future.get();
		String resultado = response.value();
		if (resultado != null) {
			return this.mapper.readValue(resultado, Armazem.class);
		}
		return null;
	}

	public Page<Armazem> getAllArmazens(int size , int page)
			throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		String uniqueID = UUID.randomUUID().toString();
		GetArmazensRequestDTO dto = new GetArmazensRequestDTO();
		dto.setPage(page);
		dto.setSize(size);
		String valueProducerRecord = mapper.writeValueAsString(dto);
		ProducerRecord<String, String> record = new ProducerRecord<>(this.topicoLerArmazem, null, uniqueID,
				valueProducerRecord);
		RequestReplyFuture<String, String, String> future = this.replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> response = future.get();
		String resultado = response.value();
		if (resultado != null) {
			var pageRequest = PageRequest.of(page, size);
			var resultadoConvertido =this.mapper.readValue(resultado,GetArmazensResponseDTO.class);
			return new PageImpl<>(resultadoConvertido.getArmazens(), pageRequest, resultadoConvertido.getTotal());
		}
		return null;
	}
}
