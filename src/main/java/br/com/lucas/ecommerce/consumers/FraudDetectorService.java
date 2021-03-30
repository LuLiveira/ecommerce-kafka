package br.com.lucas.ecommerce.consumers;

import br.com.lucas.ecommerce.kafka.KafkaService;
import br.com.lucas.ecommerce.models.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class FraudDetectorService {
	
	public static void main(String[] args) {
		var fraudeService = new FraudDetectorService();

		try(var kafkaService = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER",
				fraudeService::parse,
				Order.class,
				new HashMap<>())){
			kafkaService.run();
		}
	}

	private void parse(ConsumerRecord<String, Order> record) {
		System.out.println("-----------------------------------------------------");
		System.out.println("Processando nova ordem de compra, checando se não existe fraude.");

		System.out.println("-------------KAFKA-----------");
			System.out.println("Key >>>>> " + record.key());
			System.out.println("Value >>>>> " + record.value());
			System.out.println(record.partition());
			System.out.println(record.offset());
		System.out.println("-------------ORDER-----------");
			Order order = record.value();
			System.out.println("User id >>>>> " +order.getUserId());
			System.out.println("Order id >>>>> " +order.getOrderId());
			System.out.println("Amount >>>>> " +order.getAmount());
		System.out.println("Fim do processamento.");
	}

}
