package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ResponseMessage;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.exception.DataNotFoundException;
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
        validatePresent(id);
        categoryRepository.deleteById(id);

    }

    @Override
    public Category getCategoryById(String id) {
        if (!categoryRepository.existsById(id)){
          throw new DataNotFoundException(ResponseMessage.NOT_FOUND);}
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();

    }

    @Override
    public void updateCategory(String id, Category category){
            if(getCategoryById(id) != null){
            category.setId(id);
            categoryRepository.save(category);
            }
    }


    @Override
    public void validatePresent(String id) {
        if(!categoryRepository.findById(id).isPresent()){
            String message = ResponseMessage.NOT_FOUND;
            throw new DataNotFoundException(message);

    }
}
}
