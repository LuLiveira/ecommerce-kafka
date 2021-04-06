package br.com.lucas.ecommerce.consumers.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutionException;

public interface ConsumerFunction<T> {
    void consume (ConsumerRecord<String,T> consumerRecord) throws ExecutionException, InterruptedException;
}
