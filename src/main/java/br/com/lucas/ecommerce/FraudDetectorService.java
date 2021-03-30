package br.com.lucas.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
	
	public static void main(String[] args) {
		var fraudeService = new FraudDetectorService();

		try(var kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER",
				fraudeService::parse)){
			kafkaService.run();
		}
	}

	private void parse(ConsumerRecord<String, String> record) {
		System.out.println("-----------------------------------------------------");
		System.out.println("Processando nova ordem de compra, checando se não existe fraude.");
		System.out.println(record.key());
		System.out.println(record.value());
		System.out.println(record.partition());
		System.out.println(record.offset());
		System.out.println("Fim do processamento.");
	}

}
