package com.hostel.mess.dto;

import com.hostel.mess.entity.BookingStatus;
import com.hostel.mess.entity.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class MealBookingResponse {
    
    private Long id;
    private Long mealId;
    private MealType mealType;
    private LocalDate mealDate;
    private LocalTime mealTime;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private String notes;
}

