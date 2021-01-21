package br.com.indtextbr.services.sigoapilogistica.config;

import java.time.Duration;
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
import org.springframework.kafka.listener.BatchLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@EnableKafka
@Configuration
@Log4j2
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
	
	@Value("${spring.kafka.armazem-insert-update-delete.topico}")
	private String topicoIncluirEditarDeletarArmazem;
	
	@Value("${spring.kafka.armazem-read.topico}")
	private String topicoLerArmazem;
	

	@Value("${spring.kafka.reply.topico}")
	private String topicoReply;

	
	@Value("${spring.kafka.consumer.group-id}")
	private String grupoKafka;
	
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory, ThreadPoolTaskExecutor executor) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.getContainerProperties().setConsumerTaskExecutor(executor);
		factory.setBatchErrorHandler(new BatchLoggingErrorHandler());
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
		result.setDefaultReplyTimeout(Duration.ofMillis(40000));
		return result;
	}

	
	
	@Bean
	public NewTopic topicoIncluirEditarDeletarArmazem() {
	    return TopicBuilder.name(this.topicoIncluirEditarDeletarArmazem)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	

	
	@Bean
	public NewTopic topicoReply() {
	    return TopicBuilder.name(this.topicoReply)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
	
	@Bean
	public NewTopic topicoLerArmazensRequest() {
	    return TopicBuilder.name(this.topicoLerArmazem)
	            .partitions(1)
	            .replicas(1)
	            .config(TopicConfig.RETENTION_MS_CONFIG, this.tempoExpiracaoMensagemTopicoEmMilesegundos)
	            .build();
	}
}
