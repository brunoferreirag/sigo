package br.com.indtextbr.services.logistica.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import lombok.AccessLevel;
import lombok.Setter;

@EnableKafka
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {
	@Setter(AccessLevel.PROTECTED)
	@Value("${spring.kafka.consumer.retentativa.numero-maximo}")
	private Integer numeroMaximoRetentativa;

	@Value("${spring.kafka.consumer.retentativa.intervalo}")
	@Setter(AccessLevel.PROTECTED)
	private Long intervaloRetentativasEmMilisegundos;

	@Value("${spring.kafka.topic.tempo.expiracao}")
	@Setter(AccessLevel.PROTECTED)
	private String tempoExpiracaoMensagemTopicoEmMilesegundos;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Bean
	public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory, ThreadPoolTaskExecutor executor, DeadLetterPublishingRecoverer recover) throws Exception {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.getContainerProperties().setConsumerTaskExecutor(executor);
		var errorHandler = criarErrorHandler(recover);
		factory.setErrorHandler(errorHandler);
		configurer.configure(factory, kafkaConsumerFactory);
		return factory;
	}

	@Bean
	public DeadLetterPublishingRecoverer criarDeadLetterPublish(KafkaOperations<String, String> template) {
	    return new DeadLetterPublishingRecoverer(template);
	}
	
	@Bean
	public SeekToCurrentErrorHandler criarErrorHandler(DeadLetterPublishingRecoverer recover) {
		Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
		exceptionMap.put(InternalServerError.class, true);
		exceptionMap.put(TimeoutException.class, true);
		exceptionMap.put(ResourceAccessException.class, true);
		exceptionMap.put(Exception.class, true);

		var errorHandler = new SeekToCurrentErrorHandler(recover, new FixedBackOff(this.intervaloRetentativasEmMilisegundos, this.numeroMaximoRetentativa));

		errorHandler.setClassifications(exceptionMap, false);
		return errorHandler;

	}

}
