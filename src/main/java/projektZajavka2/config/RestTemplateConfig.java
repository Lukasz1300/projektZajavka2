package projektZajavka2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//wykonywanie żądań HTTP w aplikacjach Spring. Może być używana do komunikacji
// z zewnętrznymi API.

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
