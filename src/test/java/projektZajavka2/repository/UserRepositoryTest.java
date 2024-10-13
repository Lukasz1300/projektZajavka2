package projektZajavka2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import projektZajavka2.entity.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@TestPropertySource(locations = "classpath:application-test.yml")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        // Wstaw przykładowych użytkowników
        user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("password123");
        user1.setEmail("john.doe@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPhoneNumber("+11234567890");
        user1.setAddress("789 User St");
        user1.setCreatedAt(LocalDateTime.now());
        userRepository.save(user1);

        user2 = new User();
        user2.setUsername("tomek_doe");
        user2.setPassword("password123");
        user2.setEmail("tomek.doe@example.com");
        user2.setFirstName("Tomek");
        user2.setLastName("Doe");
        user2.setPhoneNumber("+10987654321");
        user2.setAddress("101 User St");
        user2.setCreatedAt(LocalDateTime.now());
        userRepository.save(user2);

        user3 = new User();
        user3.setUsername("alice_smith");
        user3.setPassword("password123");
        user3.setEmail("alice.smith@example.com");
        user3.setFirstName("Alice");
        user3.setLastName("Smith");
        user3.setPhoneNumber("+12345678901");
        user3.setAddress("202 User St");
        user3.setCreatedAt(LocalDateTime.now());
        userRepository.save(user3);
    }

    @Test
    public void givenUser_whenSaved_thenCanBeFoundByUsername() {
        // When
        User foundUser = userRepository.findByUsername("john_doe");

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("john_doe");
        assertThat(foundUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void givenUser_whenSaved_thenCanBeFoundByEmail() {
        // When
        User foundUser = userRepository.findByEmail("tomek.doe@example.com");

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("tomek_doe");
        assertThat(foundUser.getEmail()).isEqualTo("tomek.doe@example.com");
    }

    @Test
    public void givenUser_whenDeleted_thenShouldNotBeFound() {
        // When
        userRepository.deleteById(user3.getId());
        User foundUser = userRepository.findById(user3.getId()).orElse(null);

        // Then
        assertThat(foundUser).isNull();
    }
}
