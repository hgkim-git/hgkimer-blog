package io.github.hgkimer.privateblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.hgkimer.privateblog.domain.entity.Category;
import io.github.hgkimer.privateblog.persistence.jpa.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder().name("test2").slug("test2").build();
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
        categoryRepository.flush();
    }

    @Test
    void createCategory() {
        categoryService.createCategory(category);
        assertNotNull(category.getId());
        Long id = category.getId();
        Category found = categoryRepository.findById(id).orElseThrow();
        assertEquals(category.getId(), found.getId());
        assertEquals(category.getName(), found.getName());
        assertEquals(category.getSlug(), found.getSlug());
    }

    @Test
    void updateCategory() {
        categoryService.createCategory(category);
        Category found = categoryRepository.findById(category.getId()).orElseThrow();
        Category updatedCategory = Category.builder().name("updated").slug("updated").build();
        categoryService.updateCategory(found.getId(), updatedCategory);
        found = categoryRepository.findById(found.getId()).orElseThrow();
        assertEquals(updatedCategory.getName(), found.getName());
        assertEquals(updatedCategory.getSlug(), found.getSlug());
    }

    @Test
    public void deleteCategory() {
        categoryService.createCategory(category);
        categoryService.deleteCategory(category.getId());
        assertEquals(0, categoryRepository.findAll().size());
    }


    @Test
    void getCategoryById() {
        categoryService.createCategory(category);
        assertNotNull(category.getId());

        Category found = categoryService.getCategoryById(category.getId());
        assertEquals(category.getId(), found.getId());
        assertEquals(category.getName(), found.getName());
        assertEquals(category.getSlug(), found.getSlug());
    }
}