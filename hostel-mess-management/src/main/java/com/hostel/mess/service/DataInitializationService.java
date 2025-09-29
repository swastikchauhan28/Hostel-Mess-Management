package com.hostel.mess.service;

import com.hostel.mess.entity.*;
import com.hostel.mess.repository.SystemSettingsRepository;
import com.hostel.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class DataInitializationService implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SystemSettingsRepository systemSettingsRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUsers();
        initializeSystemSettings();
    }
    
    private void initializeDefaultUsers() {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setEmail("admin@hostelmess.com");
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("Created default admin user: admin/admin123");
        }
        
        // Create staff user if not exists
        if (!userRepository.existsByUsername("staff")) {
            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setFullName("Mess Staff");
            staff.setEmail("staff@hostelmess.com");
            staff.setRole(Role.STAFF);
            staff.setEnabled(true);
            userRepository.save(staff);
            System.out.println("Created default staff user: staff/staff123");
        }
        
        // Create sample student if not exists
        if (!userRepository.existsByUsername("student")) {
            User student = new User();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setFullName("John Doe");
            student.setEmail("john.doe@student.com");
            student.setRole(Role.STUDENT);
            student.setEnabled(true);
            userRepository.save(student);
            System.out.println("Created default student user: student/student123");
        }
    }
    
    private void initializeSystemSettings() {
        // Initialize default cutoff times if not exists
        initializeSettingIfNotExists(SystemSettings.BREAKFAST_CUTOFF_KEY, 
                SystemSettings.DEFAULT_BREAKFAST_CUTOFF.toString(), 
                "Cutoff time for breakfast bookings");
        
        initializeSettingIfNotExists(SystemSettings.LUNCH_CUTOFF_KEY, 
                SystemSettings.DEFAULT_LUNCH_CUTOFF.toString(), 
                "Cutoff time for lunch bookings");
        
        initializeSettingIfNotExists(SystemSettings.DINNER_CUTOFF_KEY, 
                SystemSettings.DEFAULT_DINNER_CUTOFF.toString(), 
                "Cutoff time for dinner bookings");
    }
    
    private void initializeSettingIfNotExists(String key, String value, String description) {
        if (!systemSettingsRepository.existsBySettingKey(key)) {
            SystemSettings setting = new SystemSettings();
            setting.setSettingKey(key);
            setting.setSettingValue(value);
            setting.setDescription(description);
            systemSettingsRepository.save(setting);
        }
    }
}

