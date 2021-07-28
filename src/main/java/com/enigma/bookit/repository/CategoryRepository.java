package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    public List<Category> findByNameContainingIgnoreCase(String name);

}
