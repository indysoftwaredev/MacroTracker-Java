package com.example.macrotracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.macrotracker.entity.Food;
import com.example.macrotracker.service.FoodService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/foods")
public class FoodController {
	
	private final FoodService foodService;
	
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}
	
	@GetMapping
	public ResponseEntity<List<Food>>getAllFoods() {
		List<Food> foods = foodService.getAllFoods();
		return ResponseEntity.ok(foods);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
		return foodService.getFoodById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Food>createFood(@Valid @RequestBody Food food) {
		Food savedFood = foodService.saveFood(food);
		return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food>updateFood(@PathVariable long id, @Valid @RequestBody Food food) {
		Food updatedFood = foodService.updateFood(id,  food);
		return ResponseEntity.ok(updatedFood);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
	    foodService.deleteFood(id);
	    return ResponseEntity.noContent().build();
	}
	
}
