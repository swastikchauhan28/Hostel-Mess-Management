package com.hostel.mess.dto;

import com.hostel.mess.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MealBookingRequest {
    
    @NotNull(message = "Meal ID is required")
    private Long mealId;
    
    @NotNull(message = "Booking status is required")
    private BookingStatus status;
    
    private String notes;
}

