package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.CategoryDTO;
import com.proyecto1t.bibliotaca_api.exceptions.DuplicateException;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Category;
import com.proyecto1t.bibliotaca_api.repository.CategoryRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new NotFoundException("Categories not found");
        }

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        categories.forEach(category -> categoryDTOs.add(mapper.toCategoryDTO(category)));
        return categoryDTOs;
    }

    public CategoryDTO findById(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Category not found");
        }
        Category category = categoryRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Category not found"));

        return mapper.toCategoryDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(categoryDTO == null) {
            throw new NotFoundException("Category not found");
        }

        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new DuplicateException("Category already exists");
        }

        Category category = mapper.toCategoryEntity(categoryDTO);
        category = categoryRepository.save(category);
        return mapper.toCategoryDTO(category);
    }

    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Category not found");
        }

        Category existingCategory = categoryRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new DuplicateException("Category not found"));

        existingCategory.setName(categoryDTO.getName());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return mapper.toCategoryDTO(updatedCategory);
    }

    public void deleteCategory(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Category not found");
        }

        Category category = categoryRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Category not found"));

        categoryRepository.deleteById(category.getId());
    }
}
