package com.campusfood.campus_food_backend.controller;

import com.campusfood.campus_food_backend.entity.FoodItem;
import com.campusfood.campus_food_backend.repository.FoodItemRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "http://localhost:5173")
public class FoodItemController {

    private final FoodItemRepository foodItemRepository;

    public FoodItemController(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    // Get all food items
    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    // Get one food item
    @GetMapping("/{id}")
    public FoodItem getFoodItem(@PathVariable Long id) {
        return foodItemRepository.findById(id).orElse(null);
    }

    // Add food item
    @PostMapping
    public FoodItem addFoodItem(@RequestBody FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    // Update food item
    @PutMapping("/{id}")
    public FoodItem updateFoodItem(
            @PathVariable Long id,
            @RequestBody FoodItem foodItem) {

        FoodItem existing = foodItemRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(foodItem.getName());
        existing.setDescription(foodItem.getDescription());
        existing.setPrice(foodItem.getPrice());
        existing.setCategory(foodItem.getCategory());
        existing.setAvailable(foodItem.isAvailable());

        return foodItemRepository.save(existing);
    }

    // Delete food item
    @DeleteMapping("/{id}")
    public String deleteFoodItem(@PathVariable Long id) {

        if (!foodItemRepository.existsById(id)) {
            return "Food item not found";
        }

        foodItemRepository.deleteById(id);

        return "Food item deleted successfully";
    }
}