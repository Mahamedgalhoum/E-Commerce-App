package com.e_Commerce.E_CommerceApp.reposiotory;

import com.e_Commerce.E_CommerceApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
