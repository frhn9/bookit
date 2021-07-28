package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Files;
import com.enigma.bookit.repository.FilesRepository;
import com.enigma.bookit.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FilesServiceImpl implements FilesService {

   @Autowired
   private FilesRepository filesRepository;

    @Override
    public Files upload(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Files files = new Files(fileName, file.getContentType(), file.getBytes());
        return filesRepository.save(files);
    }

    @Override
    public Files getFile(String id) {
        return filesRepository.findById(id).get();
    }

    @Override
    public List<Files> getAllFiles() {
        return filesRepository.findAll();
    }

}
