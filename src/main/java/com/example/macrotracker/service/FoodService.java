package com.example.macrotracker.service;

import java.util.List;
import java.util.Optional;

import com.example.macrotracker.entity.Food;

public interface FoodService {
    // Basic CRUD operations
    Food saveFood(Food food);
    Optional<Food> getFoodById(Long id);
    List<Food> getAllFoods();
    Food updateFood(Long id, Food food);
    void deleteFood(Long id);
    
    // Search and filter operations
    List<Food> searchFoodsByName(String name);
    List<Food> getFoodsByCaloriesLessThan(Double calories);
    List<Food> getFoodsByProteinRange(Double minProtein, Double maxProtein);
    
    // Validation
    boolean isFoodNameTaken(String name);
}