package br.com.compass.site.configs;

import br.com.compass.site.utils.MapUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapUtilsConfig {
    @Bean
    public MapUtil MapUtils() {

        return new MapUtil();
    }

}
