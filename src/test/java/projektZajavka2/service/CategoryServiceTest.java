package projektZajavka2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projektZajavka2.entity.Category;
import projektZajavka2.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testFindAllCategories() {
        // Przykładowe dane
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");

        // Mockowanie repozytorium
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Wywołanie metody serwisu
        List<Category> categories = categoryService.findAllCategories();

        // Sprawdzanie wyników
        assertEquals(2, categories.size());
        assertEquals("Category1", categories.get(0).getName());
        assertEquals("Category2", categories.get(1).getName());
    }

    @Test
    public void testSaveCategory() {
        Category category = new Category();
        category.setName("New Category");

        // Mockowanie repozytorium
        when(categoryRepository.save(category)).thenReturn(category);

        // Wywołanie metody serwisu
        Category savedCategory = categoryService.saveCategory(category);

        // Sprawdzanie wyników
        assertEquals("New Category", savedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategoryById() {
        Long categoryId = 1L;

        // Wywołanie metody serwisu
        categoryService.deleteCategoryById(categoryId);

        // Sprawdzanie, czy metoda deleteById została wywołana
        verify(categoryRepository, times(1)).deleteById(categoryId);

    }
}
