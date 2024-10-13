package projektZajavka2.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projektZajavka2.service.CurrencyService;

import java.util.HashMap;
import java.util.Map;

/**
 * Kontroler do obsługi żądań związanych z kursami walutowymi.
 */
@RestController
@RequestMapping("/api")
//mozna dodac adnotacje @@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    /**
     * Konstruktor wstrzykujący zależność z CurrencyService.
     *
     * @param currencyService Serwis do pobierania kursów walutowych.
     */
    @Autowired //mozna dodac adnotacje @@AllArgsConstructor
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Endpoint do pobierania aktualnego kursu USD.
     *
     * @return Kurs USD jako String.
     */
    @GetMapping("/usd-rate")
    public ResponseEntity<Map<String, String>> getUsdRate() {
        String rate = currencyService.getUsdExchangeRate();
        Map<String, String> response = new HashMap<>();
        response.put("rate", rate);
        return ResponseEntity.ok(response);
    }
}

