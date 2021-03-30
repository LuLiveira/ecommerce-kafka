package br.com.lucas.ecommerce;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		try(var kafkaDispatcherOrder = new KafkaDispatcher<Order>()) {
			try(var kafkaDispatcherEmail = new KafkaDispatcher<String>()) {

				var newOrderMain = new NewOrderMain();
				for (int i = 0; i < 10; i++) {
					var userId = UUID.randomUUID().toString();
					var orderId = UUID.randomUUID().toString();
					var amount = new BigDecimal(Math.random() * 5000 + 1);
					var order = new Order(userId, orderId, amount);
					kafkaDispatcherOrder.send("ECOMMERCE_NEW_ORDER", userId, order, newOrderMain::callback);

					var email = "Thank you for your order!";
					kafkaDispatcherEmail.send("ECOMMERCE_SEND_EMAIL", userId, email, newOrderMain::callback);
				}
			}
		}

	}

	private void callback(RecordMetadata data, Exception error) {
		if(error != null) {
			error.printStackTrace();
			return;
		}
		System.out.println("sucesso: "+data.topic().concat(" ::: ") + data.partition() + "/" + data.offset() + "/" + data.timestamp());
	}


}
