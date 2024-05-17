package com.infotech.messagereceiver;


import com.infotech.messagereceiver.entities.ProductRequestEntity;
import com.infotech.messagereceiver.repositories.ProductRequestRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.infotech.messagereceiver.environment.DotenvClass.loadDotenv;

@EnableRabbit
@SpringBootApplication
@EntityScan(basePackageClasses = ProductRequestEntity.class)
@EnableJpaRepositories(basePackageClasses = ProductRequestRepository.class)
@ComponentScan
public class MessagereceiverApplication {

	public static void main(String[] args) {
		loadDotenv();
		SpringApplication.run(MessagereceiverApplication.class, args);
	}

}
