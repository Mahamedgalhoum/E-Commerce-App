package com.e_Commerce.E_CommerceApp.reposiotory;

import com.e_Commerce.E_CommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryId(Long category_id);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryIdAndBrand(Long categoryId, String brand);

    List<Product> findByPriceBetweenOrderByPriceAsc(BigDecimal min, BigDecimal max);

    List<Product> findByRatingGreaterThanEqualOrderByRatingDesc(double rate);

    List<Product> findByNameContainingIgnoreCase(String name);
}
