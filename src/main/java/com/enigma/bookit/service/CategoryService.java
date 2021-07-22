package com.enigma.bookit.service;

import com.enigma.bookit.entity.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory (Category category);
    void deleteCategory(String id);
   Category getCategoryById(String id);
    List<Category> getAllCategory();
    void updateCategory(String id, Category category);
    void validatePresent(String id);

}
