package com.enigma.bookit.service;

import com.enigma.bookit.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface FilesService {

    public Files upload(MultipartFile file) throws IOException;
    public Files getFile(String id);
    public List<Files> getAllFiles();
}
