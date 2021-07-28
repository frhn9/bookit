package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Category;
import com.enigma.bookit.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl service;

    @MockBean
    CategoryRepository repository;

    @Test
    void addCategory() {
        Category category = new Category();
        category = new Category("c1","gym");
        when(repository.save(category)).thenReturn(category);
        service.addCategory(category);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(repository.findAll()).thenReturn(categories);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteCategory (){
        Category category = new Category();
        category = new Category("c1","gym");
        repository.save(category);
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));
        service.deleteCategory("c1");
        assertEquals(0,repository.findAll().size());
    }


    @Test
    void getCategoryById() {
        Category category = new Category();
        category = new Category("c1","gym");
        repository.save(category);
        when(repository.findById(category.getId())).thenReturn(Optional.of(category));
        Category returned = service.getCategoryById("c1");
        verify(repository).findById("c1");
        assertNotNull(returned);
    }

//
    @Test
    void getAllCategory() {
        Category category = new Category();
        category = new Category("c1","gym");
        repository.save(category);
        List<Category>categories= new ArrayList<>();
        categories.add(category);

        when(service.getAllCategory()).thenReturn(categories);
        assertEquals(1,service.getAllCategory().size());

    }

    @Test
    void updateCategory() {
        Category category = new Category();
        category = new Category("c1","gym");
        when(repository.save(category)).thenReturn(category);
        category.setName("futsal");
        repository.save(category);

        when(repository.findById(category.getId())).thenReturn(Optional.of(category));
        service.updateCategory(category.getId(), category);
            assertEquals("futsal", repository.findById("c1").get().getName());
    }
    @Test
    void findByName() {
        Category category = new Category();
        category = new Category("c1","gym");
        repository.save(category);
        when(repository.save(category)).thenReturn(category);

        service.findByName("gym");
        assertEquals("gym",category.getName());
    }

//    @Test
//    void getPerPage(){
//        Category category = new Category();
//        category = new Category("c1","gym");
//        repository.save(category);
//
//        List<Category> categories = new ArrayList<>();
//        categories.add(category);
//       Page<Category> categoryPage = new Page<Category>() {
//           @Override
//           public int getTotalPages() {
//               return 0;
//           }
//
//           @Override
//           public long getTotalElements() {
//               return 0;
//           }
//
//           @Override
//           public <U> Page<U> map(Function<? super Category, ? extends U> function) {
//               return null;
//           }
//
//           @Override
//           public int getNumber() {
//               return 0;
//           }
//
//           @Override
//           public int getSize() {
//               return 0;
//           }
//
//           @Override
//           public int getNumberOfElements() {
//               return 0;
//           }
//
//           @Override
//           public List<Category> getContent() {
//               return null;
//           }
//
//           @Override
//           public boolean hasContent() {
//               return false;
//           }
//
//           @Override
//           public Sort getSort() {
//               return null;
//           }
//
//           @Override
//           public boolean isFirst() {
//               return false;
//           }
//
//           @Override
//           public boolean isLast() {
//               return false;
//           }
//
//           @Override
//           public boolean hasNext() {
//               return false;
//           }
//
//           @Override
//           public boolean hasPrevious() {
//               return false;
//           }
//
//           @Override
//           public Pageable nextPageable() {
//               return null;
//           }
//
//           @Override
//           public Pageable previousPageable() {
//               return null;
//           }
//
//           @Override
//           public Iterator<Category> iterator() {
//               return null;
//           }
//       };
//       when(repository.findAll() anyp\())
//        repository.save(category);
//        when(service.getCategoryPerPage(any())).thenReturn(categoryPage);
//        assertEquals("gym", categoryPage.getContent().get(0).getName()) ;
//
//    }
}