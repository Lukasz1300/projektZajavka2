package projektZajavka2.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//Rola: Komunikacja z zewnętrznym API NBP.
//Klient do komunikacji z API Narodowego Banku Polskiego (NBP).
//Odpowiada za pobieranie kursu USD za pomocą REST API NBP.

//mozna @AllArgsConstructor
@Component
public class NBPClient {
    private static final String API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/USD?format=json";

    // obiekt klasy RestTemplate, który jest używany do wykonywania żądań HTTP.
    private final RestTemplate restTemplate;

    @Autowired //konstruktor przez adnotacje
    public NBPClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Metoda do wykonania żądania HTTP w celu pobrania aktualnego kursu USD
    public String getUsdRate() {
        return restTemplate.getForObject(API_URL, String.class);

    }
}
