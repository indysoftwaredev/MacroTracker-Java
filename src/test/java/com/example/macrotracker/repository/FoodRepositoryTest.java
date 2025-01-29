package com.example.macrotracker.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.macrotracker.entity.Food;

@DataJpaTest
public class FoodRepositoryTest {
	
	@Autowired
    private FoodRepository foodRepository;

    private Food chickenBreast;
    private Food salmon;
    private Food sweetPotato;
    
    @BeforeEach
    void setUp() {
    	chickenBreast = new Food("Chicken Breast", 3.6, 0.0, 31.0, 165.0);
        salmon = new Food("Salmon Fillet", 13.0, 0.0, 25.0, 208.0);
        sweetPotato = new Food("Sweet Potato", 0.0, 26.3, 2.0, 103.0);
        
        foodRepository.save(chickenBreast);
        foodRepository.save(salmon);
        foodRepository.save(sweetPotato);
    }

    @Test
    void shouldFindByNameContainingIgnoreCase() {
        // Test finding foods containing "chicken" (case insensitive)
        List<Food> chickenFoods = foodRepository.findByNameContainingIgnoreCase("chicken");
        assertThat(chickenFoods).hasSize(1);
        assertThat(chickenFoods.get(0).getName()).isEqualTo("Chicken Breast");

        // Test finding foods containing "POTATO" (case insensitive)
        List<Food> potatoFoods = foodRepository.findByNameContainingIgnoreCase("POTATO");
        assertThat(potatoFoods).hasSize(1);
        assertThat(potatoFoods.get(0).getName()).isEqualTo("Sweet Potato");
    }

    @Test
    void shouldFindByCaloriesLessThanEqual() {
        // Test finding foods with calories <= 150
        List<Food> lowCalFoods = foodRepository.findByCaloriesLessThanEqual(150.0);
        assertThat(lowCalFoods).hasSize(1);
        assertThat(lowCalFoods.get(0).getName()).isEqualTo("Sweet Potato");
    }

    @Test
    void shouldFindByProteinBetween() {
        // Test finding foods with protein between 20 and 30
        List<Food> mediumProteinFoods = foodRepository.findByProteinBetween(20.0, 30.0);
        assertThat(mediumProteinFoods).hasSize(1);
        assertThat(mediumProteinFoods.get(0).getName()).isEqualTo("Salmon Fillet");
    }

    @Test
    void shouldCheckIfFoodExists() {
        // Test checking existence by name (case insensitive)
        assertThat(foodRepository.existsByNameIgnoreCase("chicken breast")).isTrue();
        assertThat(foodRepository.existsByNameIgnoreCase("SALMON FILLET")).isTrue();
        assertThat(foodRepository.existsByNameIgnoreCase("Pizza")).isFalse();
    }

    @Test
    void shouldSaveAndRetrieveFood() {
        // Create and save a new food
        Food banana = new Food("Banana", 0.3, 23.0, 1.1, 89.0);
        Food savedBanana = foodRepository.save(banana);

        // Retrieve the saved food
        Optional<Food> retrieved = foodRepository.findById(savedBanana.getId());
        
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("Banana");
        assertThat(retrieved.get().getCarbohydrates()).isEqualTo(23.0);
    }
}
