package com.enigma.bookit.controller;

import com.enigma.bookit.entity.Category;
import com.enigma.bookit.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {


        @MockBean
        CategoryService service;

        @Autowired
        CategoryController controller;

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        private Category category;

        @BeforeEach
        void setup(){
            category = new Category();
            category.setId("c01");
            category.setName("abc");
        }

        public static String asJsonString(final Object obj){
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    @Test
    void createNewCategory() throws Exception {
            when(service.addCategory(any(Category.class))).thenReturn(category);

            RequestBuilder request = post("/api/category")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\": " + "\"c01\"" + ", \"name\" : " + "\"abc\"}");

            mockMvc.perform(request).andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message", Matchers.is("New category's data insert success!")))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.name", Matchers.is(category.getName())));

    }

    @Test
    void getCategoryByIdP() throws Exception {
        when(service.getCategoryById("c01")).thenReturn(category);
        RequestBuilder request = get("/api/category/c01")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is(category.getName())));
    }

//    @Test
//    void getAllCategory() throws Exception {
//        when(service.addCategory(any(Category.class))).thenReturn(category);
//        List<Category> categoryList = new ArrayList<>();
//        categoryList.add(category);
//        category.getId();
//        category.getName();
//        RequestBuilder request = get("/api/category")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(request).andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name", Matchers.is(category.getName())));
//
//    }

    @Test
    void updateCategory() throws Exception {
        given(service.addCategory(any(Category.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(put("/api/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(category)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(category.getName())));
    }

    @Test
    void deleteCategory() throws Exception {
        String categoryId = "c02";
        Category newCategory = new Category(categoryId,"gym");
        doNothing().when(service).deleteCategory(newCategory.getId());

        mockMvc.perform(delete("/api/category/{id}", newCategory.getId()))
                .andExpect(status().isOk());
    }
}