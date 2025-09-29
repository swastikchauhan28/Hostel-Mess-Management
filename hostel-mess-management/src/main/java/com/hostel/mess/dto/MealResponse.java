package com.hostel.mess.dto;

import com.hostel.mess.entity.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class MealResponse {
    
    private Long id;
    private MealType mealType;
    private LocalDate mealDate;
    private LocalTime cutoffTime;
    private boolean isActive;
    private boolean canBook; // Whether user can still book this meal
    private long bookedCount;
    private long skippedCount;
}

