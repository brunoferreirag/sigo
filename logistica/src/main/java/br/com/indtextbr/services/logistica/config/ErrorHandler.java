package br.com.indtextbr.services.logistica.config;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.lang.Nullable;
import org.springframework.util.backoff.BackOff;

public class ErrorHandler extends  SeekToCurrentErrorHandler {

	
	public ErrorHandler(@Nullable BiConsumer<ConsumerRecord<?, ?>, Exception> recoverer, BackOff backOff) {
		super(recoverer,backOff );
	}
	
	@Override
	public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records,
			Consumer<?, ?> consumer, MessageListenerContainer container) {
		
		super.handle(thrownException, records,consumer,container);
	}
}
