package com.e_Commerce.E_CommerceApp.service.category;

import com.e_Commerce.E_CommerceApp.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto insertCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    void deleteCategory(Long id);
}
