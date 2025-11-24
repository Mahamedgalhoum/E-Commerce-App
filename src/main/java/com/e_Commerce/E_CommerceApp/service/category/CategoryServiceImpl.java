package com.e_Commerce.E_CommerceApp.service.category;

import com.e_Commerce.E_CommerceApp.dto.CategoryDto;
import com.e_Commerce.E_CommerceApp.errors.AlreadyExistRecord;
import com.e_Commerce.E_CommerceApp.errors.ResourceNotFound;
import com.e_Commerce.E_CommerceApp.model.Category;
import com.e_Commerce.E_CommerceApp.reposiotory.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getImage());
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with ID: " + id));
        return convertToDto(category);
    }

    @Override
    public CategoryDto insertCategory(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new AlreadyExistRecord("This category already exists");
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        return convertToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category not found with ID: " + id));
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        return convertToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> { throw new ResourceNotFound("Category not found"); });
    }
}
