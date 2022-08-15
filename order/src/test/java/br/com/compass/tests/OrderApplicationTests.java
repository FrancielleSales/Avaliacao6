package br.com.compass.tests;

import br.com.compass.order.validations.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@SpringBootConfiguration
class OrderApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
	public Validations Validations() {

		return new Validations();
	}

}
