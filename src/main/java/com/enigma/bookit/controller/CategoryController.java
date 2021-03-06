package com.enigma.bookit.controller;


import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.service.CategoryService;
import com.enigma.bookit.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(ApiUrlConstant.CATEGORY)
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_OWNER')OR hasRole('ROLE_ADMIN')")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @PostMapping
    public ResponseEntity<Response<Category>> createNewCategory(@Valid @RequestBody Category category){
        Response <Category> response = new Response<>();
        String message = String.format(SuccessMessageConstant.INSERT_SUCCESS,"category's");
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(categoryService.addCategory(category));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @GetMapping("/{categoryId}")
    public  ResponseEntity<Response<Category>> getCategoryById(@PathVariable String categoryId){
            Response<Category> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
            response.setTimestamp(LocalDateTime.now());
            response.setData(categoryService.getCategoryById(categoryId));
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
    }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @GetMapping
    public ResponseEntity<Response<List<Category>>> getAllCategory(){
            Response<List<Category>> response = new Response<>();

            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
            response.setTimestamp(LocalDateTime.now());
            response.setData(categoryService.getAllCategory());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @PutMapping
    public ResponseEntity<Response<Category>> updateCategory(@RequestBody Category category){
            Response<Category> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setMessage(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL);
            response.setTimestamp(LocalDateTime.now());
            response.setData(categoryService.addCategory(category));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
     }
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity <Response> deleteCategory(@PathVariable("categoryId") String categoryId){
            Response<Category> response = new Response<>();
            response.setCode(HttpStatus.GONE.value());
            response.setStatus(HttpStatus.GONE.name());
            response.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
            response.setTimestamp(LocalDateTime.now());
            response.setData(categoryService.deleteCategory(categoryId));
            return ResponseEntity.status(HttpStatus.GONE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
    }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @GetMapping("/page")
    public Page<Category> getAllCategoryPerPage(@RequestParam(name="page", defaultValue ="0") Integer page,
                                                @RequestParam(name="size", defaultValue = "2") Integer size,
                                                @RequestParam(name="sortBy" , defaultValue = "name") String sortBy,
                                                @RequestParam(name="direction", defaultValue = "ASC") String direction){
        Sort sort =Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return categoryService.getCategoryPerPage(pageable);
    }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/search")
    public List<Category> searchCategoryByName(@RequestParam(name="name", required = false) String name){
        return categoryService.findByName(name);
    }
}

