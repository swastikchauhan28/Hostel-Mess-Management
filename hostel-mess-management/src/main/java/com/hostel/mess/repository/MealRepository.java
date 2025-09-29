package com.hostel.mess.repository;

import com.hostel.mess.entity.Meal;
import com.hostel.mess.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    
    List<Meal> findByMealDateOrderByMealType(LocalDate mealDate);
    
    List<Meal> findByMealDateAndMealType(LocalDate mealDate, MealType mealType);
    
    List<Meal> findByMealDateBetweenOrderByMealDateDescMealType(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT m FROM Meal m WHERE m.mealDate >= :date AND m.isActive = true ORDER BY m.mealDate, m.mealType")
    List<Meal> findUpcomingMeals(@Param("date") LocalDate date);
    
    @Query("SELECT m FROM Meal m WHERE m.mealDate = :date AND m.mealType = :mealType AND m.cutoffTime > :currentTime AND m.isActive = true")
    Optional<Meal> findActiveMealForBooking(@Param("date") LocalDate date, 
                                          @Param("mealType") MealType mealType, 
                                          @Param("currentTime") LocalTime currentTime);
    
    @Query("SELECT COUNT(m) FROM Meal m WHERE m.mealDate = :date AND m.mealType = :mealType")
    long countMealsByDateAndType(@Param("date") LocalDate date, @Param("mealType") MealType mealType);
}

