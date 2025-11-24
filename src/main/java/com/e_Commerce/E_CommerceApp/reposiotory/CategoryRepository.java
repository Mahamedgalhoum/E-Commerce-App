package com.e_Commerce.E_CommerceApp.reposiotory;

import com.e_Commerce.E_CommerceApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {
    boolean existsByName(String name);
}
