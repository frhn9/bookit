package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Category;
import com.enigma.bookit.repository.CategoryRepository;
import com.enigma.bookit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category deleteCategory(String id) {
        categoryRepository.deleteById(id);
        return null;
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();

    }

    @Override
    public void updateCategory(String id, Category category){
         category = categoryRepository.findById(id).get();
            category.setId(id);
            categoryRepository.save(category);
    }

    @Override
    public Page<Category> getCategoryPerPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
