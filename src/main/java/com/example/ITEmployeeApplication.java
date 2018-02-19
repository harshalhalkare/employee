package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan
@EnableAutoConfiguration 
@EnableJpaAuditing
@Configuration
@SpringBootApplication
public class ITEmployeeApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ITEmployeeApplication.class, args);
	}
}