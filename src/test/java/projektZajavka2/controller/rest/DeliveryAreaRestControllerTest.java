package projektZajavka2.controller.rest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import projektZajavka2.entity.DeliveryArea;
import projektZajavka2.entity.Restaurant;
import projektZajavka2.repository.DeliveryAreaRepository;
import projektZajavka2.repository.RestaurantRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class DeliveryAreaRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private Restaurant restaurant;
    private DeliveryArea deliveryArea;

    @Autowired
    private DeliveryAreaRepository deliveryAreaRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port; // Ustawienie portu dla RestAssured
        // Tworzenie restauracji
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("Test Restaurant Address");
        restaurant.setPhoneNumber("+123456789");
        restaurant.setEmail("restaurant@example.com");
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant = restaurantRepository.save(restaurant); // Przypisanie do pola klasy
        System.out.println(restaurant);

        // Tworzenie obiektu DeliveryArea
        deliveryArea = new DeliveryArea(); // Użyj this.deliveryArea
        deliveryArea.setRestaurant(restaurant); // Ustawienie restauracji
        deliveryArea.setStreetName("Main Street"); // Ustawienie nazwy ulicy
        this.deliveryArea = deliveryAreaRepository.save(deliveryArea);
        System.out.println(this.deliveryArea);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testGetDeliveryAreasByRestaurantId() throws Exception {
        //  poprawny identyfikator restauracji
        Long restaurantId = restaurant.getId(); // Przekazanie identyfikatora restauracji

        mockMvc.perform(get("/api/deliveryareas/restaurant/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].streetName").value("Main Street"));
    }
}
