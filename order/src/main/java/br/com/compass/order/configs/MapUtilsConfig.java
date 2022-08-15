package br.com.compass.order.configs;

import br.com.compass.order.utils.MapUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapUtilsConfig {
    @Bean
    public MapUtils MapUtils() {

        return new MapUtils();
    }

}
