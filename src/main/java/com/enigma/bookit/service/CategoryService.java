package com.enigma.bookit.service;

import com.enigma.bookit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Category addCategory (Category category);
    Category deleteCategory(String id);
   Category getCategoryById(String id);
    List<Category> getAllCategory();
    void updateCategory(String id, Category category);
    List<Category> findByName(String name);
    Page<Category> getCategoryPerPage(Pageable pageable);
}
