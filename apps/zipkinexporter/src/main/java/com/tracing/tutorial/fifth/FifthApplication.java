package com.tracing.tutorial.fifth;

import com.tracing.tutorial.fifth.service.zipkin.ZipkinProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FifthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FifthApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

	@Bean
	public ZipkinProcessor zipkinProcessor() {
		return new ZipkinProcessor();
	}
}
