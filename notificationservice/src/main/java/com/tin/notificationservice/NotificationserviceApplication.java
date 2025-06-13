package com.tin.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;


@SpringBootApplication
@ComponentScan({"com.tin.notificationservice","com.tin.commonservice"})
@RestController
public class NotificationserviceApplication {

	private Logger logger = LoggerFactory.getLogger(NotificationserviceApplication.class);

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

	@Bean
	public Consumer<Message> consumeMessage() {
		return message -> {
			EmployeeReponseModel employeeModel = circuitBreakerFactory.create("getEmployee").run(
					() -> webClientBuilder.build()
							.get()
							.uri("http://localhost:9002/api/v1/employees/" + message.getEmployeeId())
							.retrieve()
							.bodyToMono(EmployeeReponseModel.class)
							.block(),
					throwable -> {
						EmployeeReponseModel fallbackModel = new EmployeeReponseModel();
						fallbackModel.setFirstName("Anonymous");
						fallbackModel.setLastName("Employee");
						return fallbackModel;
					}
			);

			if (employeeModel != null) {
				logger.info("Consume Payload: {} {} {}", employeeModel.getFirstName(), employeeModel.getLastName(), message.getMessage());
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
