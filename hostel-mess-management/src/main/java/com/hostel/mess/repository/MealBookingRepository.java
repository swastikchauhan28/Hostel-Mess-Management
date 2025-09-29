package com.hostel.mess.repository;

import com.hostel.mess.entity.BookingStatus;
import com.hostel.mess.entity.Meal;
import com.hostel.mess.entity.MealBooking;
import com.hostel.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealBookingRepository extends JpaRepository<MealBooking, Long> {
    
    List<MealBooking> findByUserOrderByBookedAtDesc(User user);
    
    List<MealBooking> findByUserAndMeal_MealDateBetweenOrderByMeal_MealDateDesc(User user, LocalDate startDate, LocalDate endDate);
    
    List<MealBooking> findByMeal(Meal meal);
    
    List<MealBooking> findByMealAndStatus(Meal meal, BookingStatus status);
    
    @Query("SELECT mb FROM MealBooking mb WHERE mb.user = :user AND mb.meal = :meal")
    Optional<MealBooking> findByUserAndMeal(@Param("user") User user, @Param("meal") Meal meal);
    
    @Query("SELECT COUNT(mb) FROM MealBooking mb WHERE mb.meal = :meal AND mb.status = :status")
    long countByMealAndStatus(@Param("meal") Meal meal, @Param("status") BookingStatus status);
    
    @Query("SELECT mb FROM MealBooking mb WHERE mb.user = :user AND mb.meal.mealDate >= :startDate ORDER BY mb.meal.mealDate DESC, mb.meal.mealType")
    List<MealBooking> findUserBookingsFromDate(@Param("user") User user, @Param("startDate") LocalDate startDate);
    
    @Query("SELECT mb FROM MealBooking mb WHERE mb.meal.mealDate = :date AND mb.status = :status")
    List<MealBooking> findBookingsByDateAndStatus(@Param("date") LocalDate date, @Param("status") BookingStatus status);
    
    @Query("SELECT COUNT(mb) FROM MealBooking mb WHERE mb.meal.mealDate = :date AND mb.status = :status")
    long countBookingsByDateAndStatus(@Param("date") LocalDate date, @Param("status") BookingStatus status);
    
    @Query("SELECT COUNT(mb) FROM MealBooking mb WHERE mb.meal.mealDate BETWEEN :startDate AND :endDate AND mb.status = :status")
    long countBookingsByDateRangeAndStatus(@Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate, 
                                         @Param("status") BookingStatus status);
}
