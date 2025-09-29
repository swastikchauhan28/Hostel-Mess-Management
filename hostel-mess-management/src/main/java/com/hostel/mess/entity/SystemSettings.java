package com.hostel.mess.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "system_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String settingKey;
    
    @Column(nullable = false)
    private String settingValue;
    
    private String description;
    
    // Default cutoff times for each meal type
    public static final String BREAKFAST_CUTOFF_KEY = "breakfast_cutoff_time";
    public static final String LUNCH_CUTOFF_KEY = "lunch_cutoff_time";
    public static final String DINNER_CUTOFF_KEY = "dinner_cutoff_time";
    
    // Default cutoff times (can be overridden by admin)
    public static final LocalTime DEFAULT_BREAKFAST_CUTOFF = LocalTime.of(7, 0); // 7:00 AM
    public static final LocalTime DEFAULT_LUNCH_CUTOFF = LocalTime.of(10, 0); // 10:00 AM
    public static final LocalTime DEFAULT_DINNER_CUTOFF = LocalTime.of(16, 0); // 4:00 PM
}

