package com.example.macrotracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "foods")
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food name is required")
    private String name;

    @NotNull(message = "Fat content is required")
    @Min(value = 0, message = "Fat content must be greater than or equal to 0")
    private Double fat;

    @NotNull(message = "Carbohydrate content is required")
    @Min(value = 0, message = "Carbohydrate content must be greater than or equal to 0")
    private Double carbohydrates;

    @NotNull(message = "Protein content is required")
    @Min(value = 0, message = "Protein content must be greater than or equal to 0")
    private Double protein;

    @NotNull(message = "Calorie content is required")
    @Min(value = 0, message = "Calorie content must be greater than or equal to 0")
    private Double calories;

    // Default constructor required by JPA
    public Food() {
    }

    // Constructor with all fields except id
    public Food(String name, Double fat, Double carbohydrates, Double protein, Double calories) {
        this.name = name;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.calories = calories;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

}
