//package com.enigma.bookit.service.implementation;
//
//import com.enigma.bookit.entity.Facility;
//import com.enigma.bookit.entity.Files;
//import com.enigma.bookit.repository.FacilityRepository;
//import com.enigma.bookit.repository.FilesRepository;
//import com.enigma.bookit.service.FilesService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//import java.util.function.*;
//import java.util.stream.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class FilesServiceImplTest {
//    @Autowired
//    FilesService service;
//
//    @MockBean
//    FilesRepository repository;
//
//    @Test
//    void should_upload_file() throws IOException {
//        MultipartFile file = new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
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
//        Files files = new Files();
//        files.setName(fileName);
//        files.setId("1");
//        files.setType(file.getContentType());
//        files.setData(file.getBytes());
//        repository.save(files);
//        List<Files> filesList = new ArrayList<>();
//        filesList.add(files);
//
//        when(repository.findAll()).thenReturn(filesList);
//        assertEquals(1,filesList.size());
//
//
//    }
//
//    @Test
//    void should_getfile_byId() {
//        Files file = new Files();
//        file.setId("2");
//        file.setName("Pas Photo");
//        repository.save(file);
//        when(repository.findById(file.getId())).thenReturn(Optional.of(file));
//        Files returned = service.getFile("2");
//        verify(repository).findById("2");
//        assertNotNull(returned);
//    }
//
//    @Test
//    void should_expected_1_when_getallfiles_size() {
//        Files file = new Files();
//        file.setId("2");
//        file.setName("Pas Photo");
//        repository.save(file);
//        List<Files>filesList= new ArrayList<>();
//        filesList.add(file);
//        when(service.getAllFiles()).thenReturn(filesList);
//        assertEquals(1,service.getAllFiles().size());
//    }
//}