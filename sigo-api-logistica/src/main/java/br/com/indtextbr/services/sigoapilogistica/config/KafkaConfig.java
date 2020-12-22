package br.com.indtextbr.services.sigoapilogistica.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import lombok.AccessLevel;
import lombok.Setter;

@EnableKafka
@Configuration
public class KafkaConfig {
	@Value("${spring.kafka.consumer.retentativa.numero-maximo}")
	@Setter(AccessLevel.PROTECTED)
	private Integer numeroMaximoRetentativa;
	
	@Value("${spring.kafka.consumer.retentativa.intervalo}")
	@Setter(AccessLevel.PROTECTED)
	private Long intervaloRetentativasEmMilisegundos;

	@Value("${spring.kafka.topic.tempo.expiracao}")
	@Setter(AccessLevel.PROTECTED)
	private String tempoExpiracaoMensagemTopicoEmMilesegundos;
	
	@Value("${spring.kafka.armazem-inclusao.topico}")
	private String topicoIncluirArmazem;
	
	@Value("${spring.kafka.armazem-edicao.topico}")
	private String topicoEditarArmazem;

	@Value("${spring.kafka.armazem-exclusao.topico}")
	private String topicoInativarArmazem;

	@Value("${spring.kafka.armazem-get-all.request.topico}")
	private String topicoGetAllArmazensRequest;

	@Value("${spring.kafka.reply.topico}")
	private String topicoReply;

	@Value("${spring.kafka.armazem-get-by-id.request.topico}")
	private String topicoGetArmazemByIdRequest;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String grupoKafka;
	@Bean
	public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory, ThreadPoolTaskExecutor executor) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.getContainerProperties().setConsumerTaskExecutor(executor);
		factory.setRetryTemplate(retryTemplate());
		configurer.configure(factory, kafkaConsumerFactory);
		return factory;
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> pf,
			ConcurrentKafkaListenerContainerFactory<String, String> factory) {
		ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer(this.topicoReply);
		replyContainer.getContainerProperties().setMissingTopicsFatal(false);
		replyContainer.getContainerProperties().setGroupId(this.grupoKafka);
		var result = new ReplyingKafkaTemplate<>(pf, replyContainer);
		result.setSharedReplyTopic(true);
		result.setDefaultReplyTimeout(Duration.ofMillis(20000));
		return result;
	}

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
		
		FixedBackOffPolicy fixedBackOfficePolicy = new FixedBackOffPolicy();
		fixedBackOfficePolicy.setBackOffPeriod(this.intervaloRetentativasEmMilisegundos);
		retryTemplate.setBackOffPolicy(fixedBackOfficePolicy);
		return retryTemplate;
	}

	private SimpleRetryPolicy getSimpleRetryPolicy() {
		Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
		exceptionMap.put(InternalServerError.class, true);
		exceptionMap.put(TimeoutException.class, true);
		exceptionMap.put(ResourceAccessException.class, true);
		exceptionMap.put(Exception.class, true);

		return new SimpleRetryPolicy(this.numeroMaximoRetentativa, exceptionMap, true);
	}

	
	@Bean
	public NewTopic topicoIncluirArmazem() {
	    return TopicBuilder.name(this.topicoIncluirArmazem)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoInativarArmazem() {
	    return TopicBuilder.name(this.topicoInativarArmazem)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoGetAllArmazensReply() {
	    return TopicBuilder.name(this.topicoReply)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoGetAllArmazensRequest() {
	    return TopicBuilder.name(this.topicoGetAllArmazensRequest)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoGetArmazemByIdRequest() {
	    return TopicBuilder.name(this.topicoGetArmazemByIdRequest)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoEditarArmazem() {
	    return TopicBuilder.name(this.topicoEditarArmazem)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}


}
