package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<Files, String> {


}
