package com.hostel.mess.service;

import com.hostel.mess.dto.MealBookingRequest;
import com.hostel.mess.dto.MealBookingResponse;
import com.hostel.mess.entity.*;
import com.hostel.mess.repository.MealBookingRepository;
import com.hostel.mess.repository.MealRepository;
import com.hostel.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealBookingService {
    
    @Autowired
    private MealBookingRepository mealBookingRepository;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public MealBookingResponse bookMeal(MealBookingRequest bookingRequest) {
        User currentUser = getCurrentUser();
        Meal meal = mealRepository.findById(bookingRequest.getMealId())
                .orElseThrow(() -> new RuntimeException("Meal not found"));
        
        // Check if booking is allowed (before cutoff time)
        if (!isBookingAllowed(meal)) {
            throw new RuntimeException("Booking is not allowed. Cutoff time has passed.");
        }
        
        // Check if user already has a booking for this meal
        Optional<MealBooking> existingBooking = mealBookingRepository.findByUserAndMeal(currentUser, meal);
        
        MealBooking mealBooking;
        if (existingBooking.isPresent()) {
            // Update existing booking
            mealBooking = existingBooking.get();
            mealBooking.setStatus(bookingRequest.getStatus());
            mealBooking.setNotes(bookingRequest.getNotes());
            mealBooking.setBookedAt(LocalDateTime.now());
        } else {
            // Create new booking
            mealBooking = new MealBooking();
            mealBooking.setUser(currentUser);
            mealBooking.setMeal(meal);
            mealBooking.setStatus(bookingRequest.getStatus());
            mealBooking.setNotes(bookingRequest.getNotes());
        }
        
        mealBooking = mealBookingRepository.save(mealBooking);
        return createMealBookingResponse(mealBooking);
    }
    
    public List<MealBookingResponse> getUserBookings() {
        User currentUser = getCurrentUser();
        List<MealBooking> bookings = mealBookingRepository.findByUserOrderByBookedAtDesc(currentUser);
        
        List<MealBookingResponse> responses = new ArrayList<>();
        for (MealBooking booking : bookings) {
            responses.add(createMealBookingResponse(booking));
        }
        
        return responses;
    }
    
    public List<MealBookingResponse> getUserBookingsFromDate(LocalDate fromDate) {
        User currentUser = getCurrentUser();
        List<MealBooking> bookings = mealBookingRepository.findUserBookingsFromDate(currentUser, fromDate);
        
        List<MealBookingResponse> responses = new ArrayList<>();
        for (MealBooking booking : bookings) {
            responses.add(createMealBookingResponse(booking));
        }
        
        return responses;
    }
    
    public void cancelBooking(Long bookingId) {
        User currentUser = getCurrentUser();
        MealBooking booking = mealBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (!booking.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can only cancel your own bookings");
        }
        
        if (!isBookingAllowed(booking.getMeal())) {
            throw new RuntimeException("Cannot cancel booking. Cutoff time has passed.");
        }
        
        booking.setStatus(BookingStatus.CANCELLED);
        mealBookingRepository.save(booking);
    }
    
    private boolean isBookingAllowed(Meal meal) {
        return meal.getIsActive() && 
               meal.getCutoffTime().isAfter(LocalDateTime.now().toLocalTime()) &&
               meal.getMealDate().isAfter(LocalDate.now().minusDays(1));
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    private MealBookingResponse createMealBookingResponse(MealBooking booking) {
        return new MealBookingResponse(
                booking.getId(),
                booking.getMeal().getId(),
                booking.getMeal().getMealType(),
                booking.getMeal().getMealDate(),
                booking.getMeal().getCutoffTime(),
                booking.getStatus(),
                booking.getBookedAt(),
                booking.getNotes()
        );
    }
}

