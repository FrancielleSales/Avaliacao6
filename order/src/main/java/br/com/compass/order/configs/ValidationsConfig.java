package br.com.compass.order.configs;

import br.com.compass.order.validations.Validations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {

    @Bean
    public Validations Validations() {

        return new Validations();
    }
}
