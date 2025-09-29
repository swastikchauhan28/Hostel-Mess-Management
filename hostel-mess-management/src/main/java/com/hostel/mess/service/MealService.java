package com.hostel.mess.service;

import com.hostel.mess.dto.MealResponse;
import com.hostel.mess.entity.*;
import com.hostel.mess.repository.MealBookingRepository;
import com.hostel.mess.repository.MealRepository;
import com.hostel.mess.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private MealBookingRepository mealBookingRepository;
    
    @Autowired
    private SystemSettingsRepository systemSettingsRepository;
    
    public List<MealResponse> getUpcomingMeals() {
        LocalDate today = LocalDate.now();
        List<Meal> meals = mealRepository.findUpcomingMeals(today);
        
        List<MealResponse> mealResponses = new ArrayList<>();
        for (Meal meal : meals) {
            mealResponses.add(createMealResponse(meal));
        }
        
        return mealResponses;
    }
    
    public MealResponse createMealResponse(Meal meal) {
        long bookedCount = mealBookingRepository.countByMealAndStatus(meal, BookingStatus.BOOKED);
        long skippedCount = mealBookingRepository.countByMealAndStatus(meal, BookingStatus.SKIPPED);
        
        boolean canBook = meal.getIsActive() && 
                         meal.getCutoffTime().isAfter(LocalTime.now()) &&
                         meal.getMealDate().isAfter(LocalDate.now().minusDays(1));
        
        return new MealResponse(
                meal.getId(),
                meal.getMealType(),
                meal.getMealDate(),
                meal.getCutoffTime(),
                meal.getIsActive(),
                canBook,
                bookedCount,
                skippedCount
        );
    }
    
    public Meal createMeal(MealType mealType, LocalDate mealDate) {
        // Get cutoff time from system settings
        LocalTime cutoffTime = getCutoffTimeForMealType(mealType);
        
        Meal meal = new Meal();
        meal.setMealType(mealType);
        meal.setMealDate(mealDate);
        meal.setCutoffTime(cutoffTime);
        meal.setIsActive(true);
        
        return mealRepository.save(meal);
    }
    
    public List<MealResponse> getMealsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Meal> meals = mealRepository.findByMealDateBetweenOrderByMealDateDescMealType(startDate, endDate);
        
        List<MealResponse> mealResponses = new ArrayList<>();
        for (Meal meal : meals) {
            mealResponses.add(createMealResponse(meal));
        }
        
        return mealResponses;
    }
    
    private LocalTime getCutoffTimeForMealType(MealType mealType) {
        String settingKey = getSettingKeyForMealType(mealType);
        Optional<SystemSettings> setting = systemSettingsRepository.findBySettingKey(settingKey);
        
        if (setting.isPresent()) {
            return LocalTime.parse(setting.get().getSettingValue());
        }
        
        // Return default cutoff times
        return switch (mealType) {
            case BREAKFAST -> SystemSettings.DEFAULT_BREAKFAST_CUTOFF;
            case LUNCH -> SystemSettings.DEFAULT_LUNCH_CUTOFF;
            case DINNER -> SystemSettings.DEFAULT_DINNER_CUTOFF;
        };
    }
    
    private String getSettingKeyForMealType(MealType mealType) {
        return switch (mealType) {
            case BREAKFAST -> SystemSettings.BREAKFAST_CUTOFF_KEY;
            case LUNCH -> SystemSettings.LUNCH_CUTOFF_KEY;
            case DINNER -> SystemSettings.DINNER_CUTOFF_KEY;
        };
    }
}

