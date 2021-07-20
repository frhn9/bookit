package com.enigma.bookit.service;

import com.enigma.bookit.entity.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory (Category category);
    public void deleteCategory(String id);
    public Category getCategoryById(String id);
    public List<Category> getAllCategory();
    void updateCategory(String id, Category category);

}
