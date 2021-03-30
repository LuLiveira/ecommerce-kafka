package br.com.lucas.ecommerce.consumers.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {
    void consume (ConsumerRecord<String,T> consumerRecord);
}