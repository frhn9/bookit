package com.enigma.bookit.controller;


import com.enigma.bookit.entity.Category;
import com.enigma.bookit.service.CategoryService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response<Category>> createNewCategory(@RequestBody Category category){
        Response <Category> response = new Response<>();
        String message = String.format("NEW CATEGORY INSERTED");
        response.setMessage(message);
        response.setData(categoryService.addCategory(category));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @GetMapping("/{categoryId}")
    public Category getCategoryByIdP(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") String categoryId){
        categoryService.deleteCategory(categoryId);
    }
}

