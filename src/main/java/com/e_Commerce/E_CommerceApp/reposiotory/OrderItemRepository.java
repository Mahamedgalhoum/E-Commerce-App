package com.e_Commerce.E_CommerceApp.reposiotory;

import com.e_Commerce.E_CommerceApp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {
}
