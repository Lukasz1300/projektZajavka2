package projektZajavka2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor // Generuje konstruktor z wszystkimi polami
@NoArgsConstructor  // Generuje konstruktor bezargumentowy, wymagany przez JPA
@Table(name = "categories") // Zmieniono nazwę tabeli na "categories" dla zgodności z konwencjami nazw w bazie danych
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ustawia automatyczne generowanie wartości dla pola 'id', używając strategii AUTO_INCREMENT
    private Long id; // Unikalny identyfikator kategorii

    @NotBlank(message = "Nazwa kategorii nie może być pusta") // Walidacja - pole nie może być puste ani składać się wyłącznie z białych znaków
    @Size(max = 50, message = "Nazwa kategorii nie może przekraczać 50 znaków") // Walidacja - maksymalna długość nazwy to 50 znaków
    @Column(nullable = false, unique = true, length = 50) // Kolumna w bazie danych - musi być niepusta, unikalna, z maksymalną długością 50 znaków
    private String name; // Nazwa kategorii, np. "Napoje", "Dania główne"

    @ManyToMany(mappedBy = "categories") // Relacja wiele-do-wielu z encją MenuItem, gdzie 'categories' to nazwa pola w encji MenuItem
    private Set<MenuItem> menuItems = new HashSet<>(); // Zbiór pozycji menu powiązanych z tą kategorią
}
