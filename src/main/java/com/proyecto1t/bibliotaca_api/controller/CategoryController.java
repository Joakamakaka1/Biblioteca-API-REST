package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.CategoryDTO;
import com.proyecto1t.bibliotaca_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/") // -> http://localhost:8080/category/
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}") // -> http://localhost:8080/category/1
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id) {
        CategoryDTO category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/") // -> http://localhost:8080/category/
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category) {
        CategoryDTO newCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // -> http://localhost:8080/category/1
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @Valid @RequestBody CategoryDTO category) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // -> http://localhost:8080/category/1
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
