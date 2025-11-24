package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.ProductDto;
import com.e_Commerce.E_CommerceApp.request.AddProductRequest;
import com.e_Commerce.E_CommerceApp.request.UpdateProductRequest;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> products = productService.getAllProduct();
        return ResponseEntity.ok(new ApiResponse("Get all products successfully", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Product fetched successfully", product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        ProductDto newProduct = productService.insertProduct(request);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponse("Product added successfully", newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable long id,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductDto updated = productService.updateProduct(request, id);
        return ResponseEntity.ok(new ApiResponse("Product updated successfully", updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProductsByName(@RequestParam String name) {
        List<ProductDto> products = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse("Search by name successful", products));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse("Products by category fetched successfully", products));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        List<ProductDto> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse("Products by brand fetched successfully", products));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @RequestParam Long categoryId,
            @RequestParam String brand) {
        List<ProductDto> products = productService.getProductsByCategoryAndBrand(categoryId, brand);
        return ResponseEntity.ok(new ApiResponse("Filter by category and brand successful", products));
    }

    @GetMapping("/price")
    public ResponseEntity<ApiResponse> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<ProductDto> products = productService.getProductsByPriceOrderByPriceAsc(min, max);
        return ResponseEntity.ok(new ApiResponse("Products by price range fetched successfully", products));
    }

    @GetMapping("/rating")
    public ResponseEntity<ApiResponse> getProductsByRating(@RequestParam double rate) {
        List<ProductDto> products = productService.getProductsByRatingOrderByRatingDesc(rate);
        return ResponseEntity.ok(new ApiResponse("Products by rating fetched successfully", products));
    }
}
