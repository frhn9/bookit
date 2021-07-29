package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.repository.CategoryRepository;
import com.enigma.bookit.security.WebSecurityConfig;
import com.enigma.bookit.security.jwt.AuthEntryPointJwt;
import com.enigma.bookit.security.jwt.JwtUtils;
import com.enigma.bookit.security.services.UserDetailsServiceImpl;
import com.enigma.bookit.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
@Import(CategoryController.class)
class CategoryControllerTest {


    @MockBean
    CategoryService service;

    @Autowired
    CategoryController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryRepository repository;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_OWNER")
    void createNewCategory() throws Exception {
        Category category = new Category();
        category.setId("c01");
        category.setName("abc");
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
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_CUSTOMER")
    void getCategoryById() throws Exception {
        Category category = new Category();
        category.setId("c01");
        category.setName("abc");
        when(service.addCategory(any(Category.class))).thenReturn(category);
        when(service.getCategoryById("c01")).thenReturn(category);

        RequestBuilder request = get("/api/category/c01")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(category));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name", Matchers.is(category.getName())));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_ADMIN")
    void getAllCategory() {
        Category category = new Category();
        category.setId("c01");
        category.setName("abc");
        when(service.addCategory(any(Category.class))).thenReturn(category);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(service.getAllCategory()).thenReturn(categoryList);
        RequestBuilder request = get("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryList));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].name", Matchers.is(category.getName())));

    }

    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_OWNER")
    void updateCategory() throws Exception {
        Category category = new Category();
        category.setId("c01");
        category.setName("abc");
        given(service.addCategory(any(Category.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(put("/api/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(category)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", Matchers.is(category.getName())));
    }

    //
    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_OWNER")
    void deleteCategory() throws Exception {
        Category category = new Category();
        category.setId("delete01");

        when(service.deleteCategory(category.getId())).thenReturn(null);

        mockMvc.perform(delete(ApiUrlConstant.CATEGORY + "/id=" + category.getId()))
                .andExpect(jsonPath("$.code", is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_ADMIN")
    void getCustomerPerPage_shouldReturnFailedMessage() {
        Category category = new Category();

        category.setId("c01");
        category.setName("abc");

        when(service.getCategoryPerPage(any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.CATEGORY + "/page")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(category)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())));
    }
//    @SneakyThrows
//    @Test
//    void getCategoryByName() throws Exception {
//        Category category = new Category();
//        category.setId("c01");
//        category.setName("abc");
//        repository.save(category);
//        when(service.addCategory(any(Category.class))).thenReturn(category);
//        when(service.findByName(category.getName())).thenReturn(category);
//        RequestBuilder request = get("/api/category/search")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(categoryList));
//
//        mockMvc.perform(request).andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.data.name", Matchers.is(category.getName())));
//    }
}


