package projektZajavka2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Security
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/security/login") // Ignorowanie CSRF dla tych endpointów
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/users").hasRole("ADMIN")
                                .requestMatchers("/security/login").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                // Użytkownik z rolą USER ma dostęp do kategorii
                                .requestMatchers("/api/categories").hasAnyRole("ADMIN", "USER")
                                // Każdy ma dostęp do produktów
                                .requestMatchers("/api/menuItems").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/security/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
//    In-memory: użytkownicy są przechowywani w pamięci podczas działania aplikacji, ale nie są
//    trwałymi danymi.
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("testuser")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .and()
                .withUser("regularuser")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER"); // Użytkownik z rolą USER
        return authenticationManagerBuilder.build();
    }
}
