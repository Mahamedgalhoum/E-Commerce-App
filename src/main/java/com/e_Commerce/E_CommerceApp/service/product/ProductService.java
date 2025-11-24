package com.e_Commerce.E_CommerceApp.service.product;

import com.e_Commerce.E_CommerceApp.dto.ProductDto;
import com.e_Commerce.E_CommerceApp.model.Product;
import com.e_Commerce.E_CommerceApp.request.AddProductRequest;
import com.e_Commerce.E_CommerceApp.request.UpdateProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProduct();
    ProductDto getProductById(long id);
    ProductDto updateProduct(UpdateProductRequest product ,long id);
    ProductDto insertProduct(AddProductRequest product);
    void deleteProduct(long id);

    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByCategory(Long category_id);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByCategoryAndBrand(Long category_id,String brand);
    List<ProductDto> getProductsByPriceOrderByPriceAsc(BigDecimal min , BigDecimal max);
    List<ProductDto> getProductsByRatingOrderByRatingDesc(double rate);
}