package br.com.lucas.ecommerce;

import br.com.lucas.ecommerce.kafka.KafkaDispatcher;
import br.com.lucas.ecommerce.models.Email;
import br.com.lucas.ecommerce.models.Order;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		try(var kafkaDispatcherOrder = new KafkaDispatcher<Order>()) {
			try(var kafkaDispatcherEmail = new KafkaDispatcher<Email>()) {

				var newOrderMain = new NewOrderMain();
				for (int i = 0; i < 10; i++) {
					var email = Math.random() + "@email.com";
					var orderId = UUID.randomUUID().toString();
					var amount = new BigDecimal(Math.random() * 5000 + 1);
					var order = new Order(email, orderId, amount);
					kafkaDispatcherOrder.send("ECOMMERCE_NEW_ORDER", email, order, newOrderMain::callback);

					var emailSend = new Email("lcsd.lucas@gmail.com", "todos@todos.com.br", "Congrats!","Thank you for your order!");
					kafkaDispatcherEmail.send("ECOMMERCE_SEND_EMAIL", email, emailSend, newOrderMain::callback);
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
