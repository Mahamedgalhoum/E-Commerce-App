package com.e_Commerce.E_CommerceApp.reposiotory;

import com.e_Commerce.E_CommerceApp.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImage p WHERE p.product.id = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);
}
