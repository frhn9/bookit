package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager entityManager;

private Category category;
    @BeforeEach
    void setup(){
        category = new Category();
        category.setId("c1");
        category.setName("futsal");

    }
    @Test
    void shouldSave_Category_GetId() {
        Category input = categoryRepository.save(category);
        assertNotNull(entityManager.find(Category.class, input.getId()));

    }
    @Test
    void shouldFindAll_Category_GetId() {
        Category input = categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(input);
        assertNotNull(categories);
    }

    @AfterEach
    void deleteAll(){
        categoryRepository.deleteAll();
    }

}