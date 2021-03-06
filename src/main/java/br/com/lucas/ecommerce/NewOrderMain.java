package br.com.lucas.ecommerce;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Callback callback = (data, error) -> {
			if(error != null) {
				error.printStackTrace();
				return;
			}
			System.out.println("sucesso: "+data.topic().concat(" ::: ") + data.partition() + "/" + data.offset() + "/" + data.timestamp());			
		};
		
		var producer = new KafkaProducer<String, String>(properties());
		
		var value = "123,456,789";
		var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);
		
		var email = "Thank you for your order!";
		var recordEmail = new ProducerRecord<String, String>("ECOMMERCE_SEND_EMAIL", email, email);
		
		producer.send(record, callback).get();
		producer.send(recordEmail, callback).get();
//		Thread.sleep(50);
	}

	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
}
