package com.campusfood.campus_food_backend.repository;

import com.campusfood.campus_food_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}