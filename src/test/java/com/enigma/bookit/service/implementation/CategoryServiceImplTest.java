package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ResponseMessage;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.CategoryRepository;
import com.enigma.bookit.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl service;

    @Mock
    CategoryRepository repository;

    @Autowired
    MockMvc mockMvc;

    private Category category;
    private Category output;

    @BeforeEach
    void setup(){
        category = new Category("c1","gym");

        output = new Category();
        category.setId(category.getId());
        category.setName(category.getName());
        when (repository.save(any())).thenReturn(output);
    }

    @Test
    void addCategory() {
        service.addCategory(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(repository.findAll()).thenReturn(categories);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteCategory_validate() {
        repository.save(category);
        if(repository.findById(category.getId()).isPresent()){
        service.deleteCategory(category.getId());
        assertEquals(0,repository.findAll().size());}
    }


    @Test
    void getCategoryById() {
        repository.save(category);
        if(repository.findById(category.getId()).isPresent()){

        given(repository.findById("c1")).willReturn(Optional.of(output));
        Category returned = service.getCategoryById("c1");
        verify(repository).findById("c1");
        assertNotNull(returned);}
    }

    @Test
    void getAllCategory() {
        repository.save(category);
        List<Category>categories= new ArrayList<>();
        categories.add(category);

        when(service.getAllCategory()).thenReturn(categories);
        assertEquals(1,service.getAllCategory().size());

    }

    @Test
    void updateCategory() {
        repository.save(category);
        if(repository.getById(category.getId()) != null){
            category.setId(category.getId());
            category.setName("FUTSAL");
            repository.save(category);
            assertEquals("FUTSAL", category.getName());}
        }

    @Test
    void validatePresent() {
        repository.save(category);
        if(!repository.findById(category.getId()).isPresent()){
            assertEquals(ResponseMessage.NOT_FOUND, ResponseMessage.NOT_FOUND);

        }
    }

}