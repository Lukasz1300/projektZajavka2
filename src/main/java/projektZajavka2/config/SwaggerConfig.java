package projektZajavka2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;

@Configuration // Adnotacja informująca Spring, że jest to klasa konfiguracji.
public class SwaggerConfig {

    /**
     * Tworzy konfigurację grupy API dla SwaggerUI.
     *
     * @return GroupedOpenApi - obiekt konfiguracyjny dla grupy "public".
     */
    @Bean // Definiuje metodę jako bean zarządzany przez Spring.
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public") // Nazwa grupy API w SwaggerUI.
                .pathsToMatch("/api/**") // Ścieżki, które będą uwzględnione w tej grupie.
                .build(); // Buduje konfigurację grupy API.
    }
}
