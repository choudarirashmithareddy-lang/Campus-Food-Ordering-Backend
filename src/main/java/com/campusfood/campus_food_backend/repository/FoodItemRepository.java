package com.campusfood.campus_food_backend.repository;

import com.campusfood.campus_food_backend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
