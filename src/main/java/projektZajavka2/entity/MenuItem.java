package projektZajavka2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull(message = "Restauracja nie może być pusta") // Walidacja, aby restauracja nie była nullem
    private Restaurant restaurant;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nazwa pozycji menu jest wymagana") // Upewnia się, że nazwa nie jest pusta i nie zawiera tylko spacji
    @Size(max = 100, message = "Nazwa pozycji menu nie może przekraczać 100 znaków") // Ogranicza długość nazwy
    private String name;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Opis nie może przekraczać 2000 znaków") // Ogranicza długość opisu
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Cena jest wymagana") // Upewnia się, że cena nie jest nullem
    @Positive(message = "Cena musi być większa niż zero") // Upewnia się, że cena jest dodatnia
    private BigDecimal price;

    @Column(name = "image_url", length = 255)
    @Size(max = 255, message = "Adres URL obrazu nie może przekraczać 255 znaków") // Ogranicza długość URL obrazu
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "menu_item_categories",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", categories=" + categories +
                ", orderItems=" + orderItems +
                '}';
    }
}
