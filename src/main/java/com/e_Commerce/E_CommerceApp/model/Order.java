package com.e_Commerce.E_CommerceApp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // علاقة مع المستخدم
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // عناصر الطلب
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private LocalDate orderDate;

    // وقت إنشاء الطلب
    private LocalDateTime createdAt;

    // وقت تحديث الطلب
    private LocalDateTime updatedAt;

    // حالة الطلب
    private String status;

    // السعر الإجمالي بعد الجمع
    private BigDecimal totalPrice;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        status = "PENDING";
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // حساب الإجمالي
    public BigDecimal calculateTotalPrice() {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
