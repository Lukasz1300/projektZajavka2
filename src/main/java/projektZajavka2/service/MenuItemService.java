package projektZajavka2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projektZajavka2.entity.MenuItem;
import projektZajavka2.repository.MenuItemRepository;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

//        @Autowired  // wsztrzykniecie zalerznosci przez konstruktor mozna dodac adnotacje @AllArgsConstructor
//        public MenuItemService(MenuItemRepository menuItemRepository) {
//            this.menuItemRepository = menuItemRepository;
//        }

    public List<MenuItem> findAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> findMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> findMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItemById(Long id) {
        menuItemRepository.deleteById(id);
    }
}
