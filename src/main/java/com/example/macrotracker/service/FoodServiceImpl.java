package com.example.macrotracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.macrotracker.entity.Food;
import com.example.macrotracker.repository.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public Food saveFood(Food food) {
        if (isFoodNameTaken(food.getName())) {
            throw new IllegalArgumentException("A food with name '" + food.getName() + "' already exists");
        }
        return foodRepository.save(food);
    }

    @Override
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food updateFood(Long id, Food foodDetails) {
        Food food = foodRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Food not found with id: " + id));
            
        // Check if the new name is taken by a different food
        if (!food.getName().equalsIgnoreCase(foodDetails.getName()) && 
            isFoodNameTaken(foodDetails.getName())) {
            throw new IllegalArgumentException("A food with name '" + foodDetails.getName() + "' already exists");
        }

        food.setName(foodDetails.getName());
        food.setCalories(foodDetails.getCalories());
        food.setProtein(foodDetails.getProtein());
        food.setCarbohydrates(foodDetails.getCarbohydrates());
        food.setFat(foodDetails.getFat());

        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new EntityNotFoundException("Food not found with id: " + id);
        }
        foodRepository.deleteById(id);
    }

    @Override
    public List<Food> searchFoodsByName(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Food> getFoodsByCaloriesLessThan(Double calories) {
        return foodRepository.findByCaloriesLessThanEqual(calories);
    }

    @Override
    public List<Food> getFoodsByProteinRange(Double minProtein, Double maxProtein) {
        return foodRepository.findByProteinBetween(minProtein, maxProtein);
    }

    @Override
    public boolean isFoodNameTaken(String name) {
        return foodRepository.existsByNameIgnoreCase(name);
    }
}