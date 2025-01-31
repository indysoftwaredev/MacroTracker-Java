package com.example.macrotracker.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.macrotracker.entity.Food;
import com.example.macrotracker.repository.FoodRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodServiceImpl foodService;

    private Food chickenBreast;
    private Food salmon;

    @BeforeEach
    void setUp() {
        chickenBreast = new Food("Chicken Breast", 3.6, 0.0, 31.0, 165.0);
        salmon = new Food("Salmon Fillet", 13.0, 0.0, 25.0, 208.0);
    }

    @Test
    void shouldSaveNewFood() {
        // Given
        when(foodRepository.existsByNameIgnoreCase(chickenBreast.getName())).thenReturn(false);
        when(foodRepository.save(chickenBreast)).thenReturn(chickenBreast);

        // When
        Food savedFood = foodService.saveFood(chickenBreast);

        // Then
        assertThat(savedFood).isNotNull();
        assertThat(savedFood.getName()).isEqualTo("Chicken Breast");
        verify(foodRepository).save(chickenBreast);
    }

    @Test
    void shouldNotSaveFoodWithDuplicateName() {
        // Given
        when(foodRepository.existsByNameIgnoreCase(chickenBreast.getName())).thenReturn(true);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> foodService.saveFood(chickenBreast));
        verify(foodRepository, never()).save(any(Food.class));
    }

    @Test
    void shouldGetFoodById() {
        // Given
        Long id = 1L;
        when(foodRepository.findById(id)).thenReturn(Optional.of(chickenBreast));

        // When
        Optional<Food> found = foodService.getFoodById(id);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Chicken Breast");
    }

    @Test
    void shouldGetAllFoods() {
        // Given
        List<Food> expectedFoods = Arrays.asList(chickenBreast, salmon);
        when(foodRepository.findAll()).thenReturn(expectedFoods);

        // When
        List<Food> actualFoods = foodService.getAllFoods();

        // Then
        assertThat(actualFoods).hasSize(2);
        assertThat(actualFoods).containsExactlyInAnyOrder(chickenBreast, salmon);
    }

    @Test
    void shouldUpdateExistingFood() {
        // Given
        Long id = 1L;
        Food updatedFood = new Food("Chicken Breast Updated", 4.0, 0.0, 32.0, 170.0);
        when(foodRepository.findById(id)).thenReturn(Optional.of(chickenBreast));
        when(foodRepository.save(any(Food.class))).thenReturn(updatedFood);

        // When
        Food result = foodService.updateFood(id, updatedFood);

        // Then
        assertThat(result.getName()).isEqualTo("Chicken Breast Updated");
        assertThat(result.getProtein()).isEqualTo(32.0);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentFood() {
        // Given
        Long id = 999L;
        when(foodRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(EntityNotFoundException.class, () -> foodService.updateFood(id, chickenBreast));
    }

    @Test
    void shouldDeleteExistingFood() {
        // Given
        Long id = 1L;
        when(foodRepository.existsById(id)).thenReturn(true);

        // When
        foodService.deleteFood(id);

        // Then
        verify(foodRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentFood() {
        // Given
        Long id = 999L;
        when(foodRepository.existsById(id)).thenReturn(false);

        // When/Then
        assertThrows(EntityNotFoundException.class, () -> foodService.deleteFood(id));
    }

    @Test
    void shouldSearchFoodsByName() {
        // Given
        String searchTerm = "Chicken";
        List<Food> expectedFoods = Arrays.asList(chickenBreast);
        when(foodRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(expectedFoods);

        // When
        List<Food> foundFoods = foodService.searchFoodsByName(searchTerm);

        // Then
        assertThat(foundFoods).hasSize(1);
        assertThat(foundFoods.get(0).getName()).contains("Chicken");
    }

    @Test
    void shouldGetFoodsByCaloriesLessThan() {
        // Given
        Double calorieLimit = 200.0;
        List<Food> expectedFoods = Arrays.asList(chickenBreast);
        when(foodRepository.findByCaloriesLessThanEqual(calorieLimit)).thenReturn(expectedFoods);

        // When
        List<Food> foundFoods = foodService.getFoodsByCaloriesLessThan(calorieLimit);

        // Then
        assertThat(foundFoods).hasSize(1);
        assertThat(foundFoods.get(0).getCalories()).isLessThanOrEqualTo(calorieLimit);
    }
    
    @Test
    void shouldThrowExceptionWhenUpdatingFoodWithExistingName() {
       // Given
       Long id = 1L;
       Food existingFood = new Food("Chicken Breast", 3.6, 0.0, 31.0, 165.0);
       Food updatedFood = new Food("Salmon Fillet", 13.0, 0.0, 25.0, 208.0);
       
       when(foodRepository.findById(id)).thenReturn(Optional.of(existingFood));
       when(foodRepository.existsByNameIgnoreCase(updatedFood.getName())).thenReturn(true);

       // When/Then
       assertThrows(IllegalArgumentException.class, 
           () -> foodService.updateFood(id, updatedFood));
       verify(foodRepository, never()).save(any(Food.class));
    }
    
    @Test
    void shouldGetFoodsByProteinRange() {
       // Given
       Double minProtein = 20.0;
       Double maxProtein = 30.0;
       List<Food> expectedFoods = Arrays.asList(salmon); // Salmon has 25g protein
       when(foodRepository.findByProteinBetween(minProtein, maxProtein)).thenReturn(expectedFoods);

       // When
       List<Food> foundFoods = foodService.getFoodsByProteinRange(minProtein, maxProtein);

       // Then
       assertThat(foundFoods).hasSize(1);
       assertThat(foundFoods.get(0).getProtein()).isBetween(minProtein, maxProtein);
       verify(foodRepository).findByProteinBetween(minProtein, maxProtein);
    }
    
    
}
