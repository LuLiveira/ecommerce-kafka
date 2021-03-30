package br.com.lucas.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		try(var kafkaDispatcher = new KafkaDispatcher()) {
			var newOrderMain = new NewOrderMain();

			for (int i = 0; i < 10; i++) {
				kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", UUID.randomUUID().toString(), "Mensagem numero" + 1, newOrderMain::callback);
				kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", UUID.randomUUID().toString(), "Mensagem numero" + 1, newOrderMain::callback);
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
