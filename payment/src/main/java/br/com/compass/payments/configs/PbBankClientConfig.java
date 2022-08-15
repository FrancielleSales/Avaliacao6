
package br.com.compass.payments.configs;

import br.com.compass.payments.clients.PbBankClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PbBankClientConfig {

    @Bean
    public PbBankClient PbBankClient() {

        return new PbBankClient();
    }
}