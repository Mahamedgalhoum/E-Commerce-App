package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.CategoryDto;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        return ResponseEntity.ok(
                new ApiResponse("Get Categories Success", categoryService.getAllCategories())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse("Category fetched successfully", categoryService.getCategoryById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insertCategory(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Category added successfully", categoryService.insertCategory(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse("Category updated successfully", categoryService.updateCategory(dto, id))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
    }
}
