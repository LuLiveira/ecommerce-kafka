package br.com.lucas.ecommerce.consumers;

import br.com.lucas.ecommerce.consumers.models.Order;
import br.com.lucas.ecommerce.kafka.KafkaDispatcher;
import br.com.lucas.ecommerce.kafka.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {
	
	public static void main(String[] args) {
		var fraudeService = new FraudDetectorService();

		try(var kafkaService = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER",
				fraudeService::parse,
				Order.class,
				Map.of())){
			kafkaService.run();
		}
	}

	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

	private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
		System.out.println("-----------------------------------------------------");
		System.out.println("Processando nova ordem de compra, checando se não existe fraude.");

		System.out.println("-------------KAFKA-----------");
			System.out.println("Key >>>>> " + record.key());
			System.out.println("Value >>>>> " + record.value());
			System.out.println(record.partition());
			System.out.println(record.offset());
		System.out.println("-------------ORDER-----------");
			Order order = record.value();
			System.out.println("User email >>>>> " + order.getEmail());
			System.out.println("Order id >>>>> " + order.getOrderId());
			System.out.println("Amount >>>>> " + order.getAmount());

			if(isFraud(order)){
				System.out.println("Fraude.");
				orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getEmail(), order, (success, failure) -> {});
			}else {
				System.out.println("Aprovada.");
				orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getEmail(), order, (success, failure) -> {});
			}

		System.out.println("Fim do processamento.");
	}

	private boolean isFraud(Order order) {
		return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
	}

}
