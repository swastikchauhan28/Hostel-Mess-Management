package com.hostel.mess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class DashboardResponse {
    
    private LocalDate date;
    private List<MealCount> mealCounts;
    private long totalBooked;
    private long totalSkipped;
    private long totalStudents;
    private double foodWastagePercentage;
    
    @Data
    @AllArgsConstructor
    public static class MealCount {
        private String mealType;
        private long booked;
        private long skipped;
        private long total;
    }
}

