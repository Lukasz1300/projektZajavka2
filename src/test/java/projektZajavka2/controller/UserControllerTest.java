package projektZajavka2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import projektZajavka2.entity.User;
import projektZajavka2.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class) // Testowanie tylko kontrolera UserController
@WithMockUser(username = "testuser", roles = {"ADMIN"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnAllUsers() throws Exception {
        List<User> users = List.of(new User(), new User());
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())  // Oczekiwany status 200 OK
                .andExpect(view().name("user/list"))  // Oczekiwany widok
                .andExpect(model().attribute("users", users));  // Oczekiwany model z użytkownikami
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        Long id = 1L;
        User user = new User();
        when(userService.findUserById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("user/detail"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void shouldReturn404WhenUserNotFound() throws Exception {
        String username = "nonExistentUser";

        // Użycie Optional.empty() do symulacji braku użytkownika
        when(userService.findUserByUserName(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/username/" + username))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void shouldReturnUserByUserName() throws Exception {
        String username = "testUser";
        User user = new User();
        user.setUsername(username); // Ustawienie username dla obiektu User

        // Użycie Optional do zwrócenia użytkownika
        when(userService.findUserByUserName(username)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/username/" + username))
                .andExpect(status().isOk())
                .andExpect(view().name("user/detail"))
                .andExpect(model().attribute("user", user));
    }


    @Test
    public void shouldReturnUserByEmail() throws Exception {
        String email = "test@example.com";
        User user = new User();
        when(userService.findUserByEmail(email)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/email/" + email))
                .andExpect(status().isOk())
                .andExpect(view().name("user/detail"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void shouldReturn404WhenUserByEmailNotFound() throws Exception {
        // Given
        String email = "test@example.com";
        when(userService.findUserByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/users/email/" + email))
                .andExpect(status().isNotFound())  // Oczekiwany kod statusu 404
                .andExpect(view().name("error/404")) // Oczekiwany widok 404
                .andExpect(model().attribute("error", "User not found"));  // Sprawdza, czy model zawiera odpowiedni komunikat
    }

    @Test
    public void shouldShowNewUserForm() throws Exception {
        // Wykonuje żądanie GET na endpoint "/users/new"
        mockMvc.perform(get("/users/new"))
                // Sprawdza, czy odpowiedź ma status 200 (OK)
                .andExpect(status().isOk())
                // Sprawdza, czy widok to "user/form"
                .andExpect(view().name("user/form"))
                // Weryfikuje, że model zawiera atrybut "user" z nowym obiektem User
                .andExpect(model().attribute("user", allOf(
                        // Sprawdza, że id jest null
                        hasProperty("id", is(nullValue())),
                        // Sprawdza, że username jest null
                        hasProperty("username", is(nullValue())),
                        // Sprawdza, że password jest null
                        hasProperty("password", is(nullValue())),
                        // Sprawdza, że email jest null
                        hasProperty("email", is(nullValue())),
                        // Sprawdza, że firstName jest null
                        hasProperty("firstName", is(nullValue())),
                        // Sprawdza, że lastName jest null
                        hasProperty("lastName", is(nullValue())),
                        // Sprawdza, że phoneNumber jest null
                        hasProperty("phoneNumber", is(nullValue())),
                        // Sprawdza, że address jest null
                        hasProperty("address", is(nullValue()))
                )));
    }

    @Test
    public void shouldSaveUser() throws Exception {
        // Przygotowanie: nie musisz tworzyć konkretnego obiektu User
        User user = new User(); // lub w ogóle możesz tego nie robić

        // Zdefiniowanie zachowania mocka
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Wykonanie żądania POST
        mockMvc.perform(post("/users")
                        .flashAttr("user", user) // Możesz użyć konkretnego użytkownika lub pominąć
                        .with(csrf())) // Dodaj token CSRF
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        // Weryfikacja, czy metoda saveUser została wywołana
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        // Given
        Long id = 1L;
        // When & Then
        mockMvc.perform(delete("/users/delete/" + id)
                        .with(csrf())) // Dodaj token CSRF
                .andExpect(status().is3xxRedirection()) // Sprawdzenie, czy odpowiedź jest przekierowaniem (302)
                .andExpect(redirectedUrl("/users"));  // Sprawdzenie, czy przekierowuje na listę użytkowników
        // Weryfikacja, czy metoda usuwania została wywołana raz
        verify(userService, times(1)).deleteUserById(id);
    }
}
