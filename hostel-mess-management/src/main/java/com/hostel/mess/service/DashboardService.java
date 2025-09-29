package com.hostel.mess.service;

import com.hostel.mess.dto.DashboardResponse;
import com.hostel.mess.entity.BookingStatus;
import com.hostel.mess.entity.MealType;
import com.hostel.mess.repository.MealBookingRepository;
import com.hostel.mess.repository.MealRepository;
import com.hostel.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    
    @Autowired
    private MealBookingRepository mealBookingRepository;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public DashboardResponse getDashboardData(LocalDate date) {
        List<DashboardResponse.MealCount> mealCounts = new ArrayList<>();
        long totalBooked = 0;
        long totalSkipped = 0;
        
        for (MealType mealType : MealType.values()) {
            long booked = mealBookingRepository.countBookingsByDateAndStatus(date, BookingStatus.BOOKED);
            long skipped = mealBookingRepository.countBookingsByDateAndStatus(date, BookingStatus.SKIPPED);
            
            mealCounts.add(new DashboardResponse.MealCount(
                    mealType.name(),
                    booked,
                    skipped,
                    booked + skipped
            ));
            
            totalBooked += booked;
            totalSkipped += skipped;
        }
        
        long totalStudents = userRepository.countActiveUsersByRole(com.hostel.mess.entity.Role.STUDENT);
        double foodWastagePercentage = totalStudents > 0 ? 
                ((double) (totalStudents - totalBooked) / totalStudents) * 100 : 0;
        
        return new DashboardResponse(
                date,
                mealCounts,
                totalBooked,
                totalSkipped,
                totalStudents,
                foodWastagePercentage
        );
    }
    
    public DashboardResponse getWeeklyReport(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        
        List<DashboardResponse.MealCount> mealCounts = new ArrayList<>();
        long totalBooked = 0;
        long totalSkipped = 0;
        
        for (MealType mealType : MealType.values()) {
            long booked = mealBookingRepository.countBookingsByDateRangeAndStatus(
                    startDate, endDate, BookingStatus.BOOKED);
            long skipped = mealBookingRepository.countBookingsByDateRangeAndStatus(
                    startDate, endDate, BookingStatus.SKIPPED);
            
            mealCounts.add(new DashboardResponse.MealCount(
                    mealType.name(),
                    booked,
                    skipped,
                    booked + skipped
            ));
            
            totalBooked += booked;
            totalSkipped += skipped;
        }
        
        long totalStudents = userRepository.countActiveUsersByRole(com.hostel.mess.entity.Role.STUDENT);
        double foodWastagePercentage = totalStudents > 0 ? 
                ((double) (totalStudents * 21 - totalBooked) / (totalStudents * 21)) * 100 : 0; // 21 meals per week
        
        return new DashboardResponse(
                startDate,
                mealCounts,
                totalBooked,
                totalSkipped,
                totalStudents,
                foodWastagePercentage
        );
    }
}

