package com.hostel.mess.controller;

import com.hostel.mess.dto.MealBookingRequest;
import com.hostel.mess.dto.MealBookingResponse;
import com.hostel.mess.dto.MealResponse;
import com.hostel.mess.service.MealBookingService;
import com.hostel.mess.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
@Tag(name = "Student", description = "Student management APIs")
public class StudentController {
    
    @Autowired
    private MealService mealService;
    
    @Autowired
    private MealBookingService mealBookingService;
    
    @GetMapping("/meals/upcoming")
    @Operation(summary = "Get upcoming meals", description = "Get list of upcoming meals that can be booked")
    public ResponseEntity<List<MealResponse>> getUpcomingMeals() {
        List<MealResponse> meals = mealService.getUpcomingMeals();
        return ResponseEntity.ok(meals);
    }
    
    @PostMapping("/bookings")
    @Operation(summary = "Book or skip a meal", description = "Book or skip a meal before cutoff time")
    public ResponseEntity<?> bookMeal(@Valid @RequestBody MealBookingRequest bookingRequest) {
        try {
            MealBookingResponse response = mealBookingService.bookMeal(bookingRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/bookings")
    @Operation(summary = "Get user bookings", description = "Get all bookings for the current user")
    public ResponseEntity<List<MealBookingResponse>> getUserBookings() {
        List<MealBookingResponse> bookings = mealBookingService.getUserBookings();
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/bookings/history")
    @Operation(summary = "Get booking history", description = "Get booking history from a specific date")
    public ResponseEntity<List<MealBookingResponse>> getBookingHistory(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(30)}") LocalDate fromDate) {
        List<MealBookingResponse> bookings = mealBookingService.getUserBookingsFromDate(fromDate);
        return ResponseEntity.ok(bookings);
    }
    
    @DeleteMapping("/bookings/{bookingId}")
    @Operation(summary = "Cancel booking", description = "Cancel a meal booking before cutoff time")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            mealBookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

