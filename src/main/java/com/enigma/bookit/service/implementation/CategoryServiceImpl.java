package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Category;
import com.enigma.bookit.repository.CategoryRepository;
import com.enigma.bookit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);

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
    public void updateCategory(String id, Category category) {
        if(getCategoryById(id) != null){
            category.setId(id);
            categoryRepository.save(category);
        }
    }

}
