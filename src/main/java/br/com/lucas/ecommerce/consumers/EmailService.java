package br.com.lucas.ecommerce.consumers;

import br.com.lucas.ecommerce.kafka.KafkaService;
import br.com.lucas.ecommerce.models.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {
	
	public static void main(String[] args) {
		var emailService = new EmailService();
		try(var service = new KafkaService<>(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL",
				emailService::parse,
				Email.class,
				new HashMap<>())){
			service.run();
		}
	}

	private void parse(ConsumerRecord<String, Email> record) {
		System.out.println("-----------------------------------------------------");
		System.out.println("Send email");
		System.out.println(record.key());
		System.out.println(record.value());
		System.out.println(record.partition());
		System.out.println(record.offset());

		System.out.println("-------------EMAIL-----------");
			Email email = record.value();
			System.out.println("Body >>>>> " +email.getBody());
			System.out.println("From >>>>> " +email.getFrom());
			System.out.println("Subject >>>>> " +email.getSubject());
			System.out.println("To >>>>> " + email.getTo());

		System.out.println("Email sent");
	}
}
