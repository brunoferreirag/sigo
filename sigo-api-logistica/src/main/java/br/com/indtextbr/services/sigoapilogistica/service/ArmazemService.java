package br.com.indtextbr.services.sigoapilogistica.service;

import java.util.List;
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
import br.com.indtextbr.services.sigoapilogistica.model.Armazem;

@Service
public class ArmazemService {

	@Value("${spring.kafka.armazem-inclusao.topico}")
	private String topicoIncluirArmazem;
	
	@Value("${spring.kafka.armazem-edicao.topico}")
	private String topicoEditarArmazem;

	@Value("${spring.kafka.armazem-inativar.topico}")
	private String topicoInativarArmazem;

	@Value("${spring.kafka.armazem-get-all.request.topico}")
	private String topicoGetAllArmazensRequest;

	@Value("${spring.kafka.armazem-get-all.reply.topico}")
	private String topicoGetAllArmazensReply;

	@Value("${spring.kafka.armazem-get-all.request.topico}")
	private String topicoGetArmazemByIdRequest;

	@Value("${spring.kafka.armazem-get-all.reply.topico}")
	private String topicoGetArmazemByIdReply;

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
		String payload = this.mapper.writeValueAsString(armazem);
		this.kafkaTemplate.send(this.topicoIncluirArmazem, payload);
	}
	
	public void editarArmazem(Armazem armazem) throws JsonProcessingException {
		String payload = this.mapper.writeValueAsString(armazem);
		this.kafkaTemplate.send(this.topicoEditarArmazem, payload);
	}
	
	public void inativarArmazem(String id) throws JsonProcessingException {
		String payload = this.mapper.writeValueAsString(id);
		this.kafkaTemplate.send(this.topicoInativarArmazem, payload);
	}

	public Armazem getArmazemById(String id)
			throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		ProducerRecord<String, String> record = new ProducerRecord<>(this.topicoGetArmazemByIdRequest, null, id, id);
		RequestReplyFuture<String, String, String> future = this.replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> response = future.get();
		String resultado = response.value();
		if (resultado != null) {
			return this.mapper.readValue(resultado, Armazem.class);
		}
		return null;
	}

	public List<Armazem> getAllArmazens()
			throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		String uniqueID = UUID.randomUUID().toString();
		String valueProducerRecord = "";
		ProducerRecord<String, String> record = new ProducerRecord<>(this.topicoGetArmazemByIdRequest, null, uniqueID,
				valueProducerRecord);
		RequestReplyFuture<String, String, String> future = this.replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> response = future.get();
		String resultado = response.value();
		if (resultado != null) {
			return this.mapper.readValue(resultado,
					mapper.getTypeFactory().constructCollectionType(List.class, Armazem.class));
		}
		return null;
	}
}
