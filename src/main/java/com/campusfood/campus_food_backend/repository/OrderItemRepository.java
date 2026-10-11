package com.campusfood.campus_food_backend.repository;

import com.campusfood.campus_food_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}  
