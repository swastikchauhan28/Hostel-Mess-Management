package com.hostel.mess.controller;

import com.hostel.mess.dto.DashboardResponse;
import com.hostel.mess.dto.MealResponse;
import com.hostel.mess.service.DashboardService;
import com.hostel.mess.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff", description = "Staff management APIs")
public class StaffController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private MealService mealService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard data", description = "Get meal-wise headcount and statistics for a specific date")
    public ResponseEntity<DashboardResponse> getDashboardData(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate date) {
        DashboardResponse response = dashboardService.getDashboardData(date);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/dashboard/weekly")
    @Operation(summary = "Get weekly report", description = "Get weekly statistics and food wastage report")
    public ResponseEntity<DashboardResponse> getWeeklyReport(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") LocalDate startDate) {
        DashboardResponse response = dashboardService.getWeeklyReport(startDate);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/meals")
    @Operation(summary = "Get meals by date range", description = "Get meals within a date range for reporting")
    public ResponseEntity<List<MealResponse>> getMealsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<MealResponse> meals = mealService.getMealsByDateRange(startDate, endDate);
        return ResponseEntity.ok(meals);
    }
}

