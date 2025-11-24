package com.e_Commerce.E_CommerceApp.service.product;

import com.e_Commerce.E_CommerceApp.dto.ProductDto;
import com.e_Commerce.E_CommerceApp.errors.ResourceNotFound;
import com.e_Commerce.E_CommerceApp.model.Category;
import com.e_Commerce.E_CommerceApp.model.Product;
import com.e_Commerce.E_CommerceApp.model.ProductImage;
import com.e_Commerce.E_CommerceApp.reposiotory.CategoryRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.ProductImageRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.ProductRepository;
import com.e_Commerce.E_CommerceApp.request.AddProductRequest;
import com.e_Commerce.E_CommerceApp.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductDto> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found with ID: " + id));
        return convertProductToDto(product);
    }

    @Override
    public ProductDto updateProduct(UpdateProductRequest request, long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found with ID: " + id));

        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResourceNotFound("Category not found with ID: " + request.getCategory()));

        // تحديث الخصائص الأساسية
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setCategory(category);

        // حذف الصور القديمة
        productImageRepository.deleteAllByProductId(existingProduct.getId());

        // إضافة الصور الجديدة
        List<ProductImage> newImages = new ArrayList<>();
        for (String imgUrl : request.getImages()) {
            ProductImage image = new ProductImage(imgUrl);
            image.setProduct(existingProduct);
            newImages.add(image);
        }
        existingProduct.setImages(newImages);

        Product saved = productRepository.save(existingProduct);
        return convertProductToDto(saved);
    }

    @Override
    public ProductDto insertProduct(AddProductRequest request) {
        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResourceNotFound("Category not found with ID: " + request.getCategory()));

        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setPrice(request.getPrice());
        newProduct.setDescription(request.getDescription());
        newProduct.setBrand(request.getBrand());
        newProduct.setQuantity(request.getQuantity());
        newProduct.setCategory(category);

        List<ProductImage> images = new ArrayList<>();
        for (String imgUrl : request.getImages()) {
            ProductImage image = new ProductImage(imgUrl);
            image.setProduct(newProduct);
            images.add(image);
        }
        newProduct.setImages(images);

        Product saved = productRepository.save(newProduct);
        return convertProductToDto(saved);
    }

    @Override
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Cannot delete. Product not found with ID: " + id));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) throw new ResourceNotFound("No products found containing name: " + name);
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) throw new ResourceNotFound("Category not found with ID: " + categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) throw new ResourceNotFound("No products found in this category");
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if (products.isEmpty()) throw new ResourceNotFound("No products found for brand: " + brand);
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(Long categoryId, String brand) {
        if (!categoryRepository.existsById(categoryId)) throw new ResourceNotFound("Category not found with ID: " + categoryId);
        List<Product> products = productRepository.findByCategoryIdAndBrand(categoryId, brand);
        if (products.isEmpty()) throw new ResourceNotFound("No products found for this category and brand");
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByPriceOrderByPriceAsc(BigDecimal min, BigDecimal max) {
        List<Product> products = productRepository.findByPriceBetweenOrderByPriceAsc(min, max);
        if (products.isEmpty()) throw new ResourceNotFound("No products found in the price range");
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByRatingOrderByRatingDesc(double rate) {
        List<Product> products = productRepository.findByRatingGreaterThanEqualOrderByRatingDesc(rate);
        if (products.isEmpty()) throw new ResourceNotFound("No products found with rating >= " + rate);
        return products.stream().map(this::convertProductToDto).collect(Collectors.toList());
    }

    // دالة تحويل المنتج إلى DTO
    private ProductDto convertProductToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setQuantity(product.getQuantity());
        dto.setRating(product.getRating());
        dto.setCategoryName(product.getCategoryName());
        if (product.getImages() != null) {
            dto.setImages(product.getImages().stream()
                    .map(ProductImage::getImage)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
