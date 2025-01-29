package com.example.macrotracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.macrotracker.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    // Find foods by name containing the search string (case-insensitive)
    List<Food> findByNameContainingIgnoreCase(String name);
    
    // Find foods with calories less than or equal to the specified value
    List<Food> findByCaloriesLessThanEqual(Double calories);
    
    // Find foods within a protein range
    List<Food> findByProteinBetween(Double minProtein, Double maxProtein);
    
    // Check if a food with exact name exists
    boolean existsByNameIgnoreCase(String name);
}