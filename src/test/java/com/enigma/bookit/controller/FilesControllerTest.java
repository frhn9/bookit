//package com.enigma.bookit.controller;
//
//import com.enigma.bookit.constant.ApiUrlConstant;
//import com.enigma.bookit.entity.Book;
//import com.enigma.bookit.entity.Files;
//import com.enigma.bookit.repository.CategoryRepository;
//import com.enigma.bookit.repository.FilesRepository;
//import com.enigma.bookit.service.CategoryService;
//import com.enigma.bookit.service.FilesService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(FilesController.class)
//@Import(FilesController.class)
//class FilesControllerTest {
//    @MockBean
//    FilesService service;
//
//    @Autowired
//    FilesController controller;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    FilesRepository repository;
//
//    public static String asJsonString(final Object obj){
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @Test
//    void getFile() throws Exception {
//        MultipartFile file = new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public String getOriginalFilename() {
//                return null;
//            }
//
//            @Override
//            public String getContentType() {
//                return null;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public long getSize() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBytes() throws IOException {
//                return new byte[0];
//            }
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return null;
//            }
//
//            @Override
//            public void transferTo(File file) throws IOException, IllegalStateException {
//
//            }
//        };
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Files files = new Files(fileName, file.getContentType(),);
//        repository.save(files);
//        files.setId("2");
//        files.setData(file.getBytes());
//        files.setType("image/jpeg");
//        when(service.getFile("2")).thenReturn(files);
//
//        mockMvc.perform(get("/files")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(file)).accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", Matchers.is(file.getName())));
//    }
//}