package br.com.lucas.ecommerce;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class FraudDetectorOrder {
	
	public static void main(String[] args) {
		
		var consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));
		
		while(true) {
			var records = consumer.poll(Duration.ofMillis(500));
			
			if(!records.isEmpty()) {
				System.out.println("Encontrei "+records.count()+" registros.");
				for (var record : records) {
					System.out.println("-----------------------------------------------------");
					System.out.println("Processando nova ordem de compra, checando se não existe fraude.");
					System.out.println(record.key());
					System.out.println(record.value());
					System.out.println(record.partition());
					System.out.println(record.offset());
					System.out.println("Fim do processamento.");	
				}
			}
		}
		
	}

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorOrder.class.getSimpleName());
		return properties;
		
	}

}
