package com.example.macrotracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.macrotracker.entity.Food;
import com.example.macrotracker.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest
public class FoodControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean //SpyBean is a newer annotation.  Check Spring Docs for details and differences.
	private FoodService foodService;
	
	private Food chickenBreast;
	private Food salmon;
	
	@BeforeEach
	void setUp() {
		chickenBreast = new Food("Chicken Breast", 3.6, 0.0, 31.0, 165.0);
		chickenBreast.setId(1L);
		salmon = new Food("Salmon Fillet", 13.0, 0.0, 25.0, 208.0);
		salmon.setId(2L);
	}

    @Test
    void shouldReturnAllFoods() throws Exception {
        when(foodService.getAllFoods()).thenReturn(Arrays.asList(chickenBreast, salmon));

        mockMvc.perform(get("/api/foods"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Chicken Breast"))
                .andExpect(jsonPath("$[1].name").value("Salmon Fillet"));
    }

    @Test
    void shouldReturnFoodWhenExists() throws Exception {
        when(foodService.getFoodById(1L)).thenReturn(Optional.of(chickenBreast));

        mockMvc.perform(get("/api/foods/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Chicken Breast"))
                .andExpect(jsonPath("$.protein").value(31.0));
    }

    @Test
    void shouldReturn404WhenFoodNotFound() throws Exception {
        when(foodService.getFoodById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/foods/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldCreateNewFood() throws Exception {
        Food newFood = new Food("Apple", 0.2, 25.0, 0.3, 95.0);
        when(foodService.saveFood(any(Food.class))).thenReturn(newFood);

        mockMvc.perform(post("/api/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFood)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Apple"));    	
    }

	@Test
	void shouldReturn400WhenInvalidFood() throws Exception {
	    Food invalidFood = new Food(null, -1.0, 25.0, 0.3, 95.0);
	
	    mockMvc.perform(post("/api/foods")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(invalidFood)))
	            .andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldUpdateExistingFood() throws Exception {
	    Food updatedFood = new Food("Updated Chicken", 4.0, 0.0, 32.0, 170.0);
	    when(foodService.updateFood(eq(1L), any(Food.class))).thenReturn(updatedFood);

	    mockMvc.perform(put("/api/foods/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updatedFood)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name").value("Updated Chicken"))
	            .andExpect(jsonPath("$.protein").value(32.0));
	}

	@Test
	void shouldReturn404WhenUpdatingNonExistentFood() throws Exception {
	    Food updatedFood = new Food("Updated Chicken", 4.0, 0.0, 32.0, 170.0);
	    when(foodService.updateFood(eq(999L), any(Food.class)))
	            .thenThrow(new EntityNotFoundException("Food not found with id: 999"));
	
	    mockMvc.perform(put("/api/foods/999")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updatedFood)))
	            .andExpect(status().isNotFound());
	}
	
	@Test
	void shouldDeleteExistingFood() throws Exception {
	    doNothing().when(foodService).deleteFood(1L);

	    mockMvc.perform(delete("/api/foods/1"))
	            .andExpect(status().isNoContent());
	}

	@Test
	void shouldReturn404WhenDeletingNonExistentFood() throws Exception {
	    doThrow(new EntityNotFoundException("Food not found with id: 999"))
	            .when(foodService).deleteFood(999L);

	    mockMvc.perform(delete("/api/foods/999"))
	            .andExpect(status().isNotFound());
	}
    
    
	
}
