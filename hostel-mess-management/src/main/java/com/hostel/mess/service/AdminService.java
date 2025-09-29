package com.hostel.mess.service;

import com.hostel.mess.dto.SystemSettingsRequest;
import com.hostel.mess.dto.UserResponse;
import com.hostel.mess.entity.Role;
import com.hostel.mess.entity.SystemSettings;
import com.hostel.mess.entity.User;
import com.hostel.mess.repository.SystemSettingsRepository;
import com.hostel.mess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SystemSettingsRepository systemSettingsRepository;
    
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        
        for (User user : users) {
            responses.add(new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getEnabled(),
                    user.getCreatedAt()
            ));
        }
        
        return responses;
    }
    
    public List<UserResponse> getUsersByRole(Role role) {
        List<User> users = userRepository.findActiveUsersByRole(role);
        List<UserResponse> responses = new ArrayList<>();
        
        for (User user : users) {
            responses.add(new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getEnabled(),
                    user.getCreatedAt()
            ));
        }
        
        return responses;
    }
    
    public UserResponse updateUserStatus(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setEnabled(enabled);
        user = userRepository.save(user);
        
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getEnabled(),
                user.getCreatedAt()
        );
    }
    
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        
        userRepository.deleteById(userId);
    }
    
    public SystemSettings updateSystemSetting(SystemSettingsRequest request) {
        Optional<SystemSettings> existingSetting = systemSettingsRepository.findBySettingKey(request.getSettingKey());
        
        SystemSettings setting;
        if (existingSetting.isPresent()) {
            setting = existingSetting.get();
            setting.setSettingValue(request.getSettingValue());
            setting.setDescription(request.getDescription());
        } else {
            setting = new SystemSettings();
            setting.setSettingKey(request.getSettingKey());
            setting.setSettingValue(request.getSettingValue());
            setting.setDescription(request.getDescription());
        }
        
        return systemSettingsRepository.save(setting);
    }
    
    public List<SystemSettings> getAllSystemSettings() {
        return systemSettingsRepository.findAll();
    }
    
    public SystemSettings getSystemSetting(String settingKey) {
        return systemSettingsRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
    }
    
    public void updateCutoffTime(com.hostel.mess.entity.MealType mealType, LocalTime cutoffTime) {
        String settingKey = getSettingKeyForMealType(mealType);
        
        SystemSettings setting = systemSettingsRepository.findBySettingKey(settingKey)
                .orElse(new SystemSettings());
        
        setting.setSettingKey(settingKey);
        setting.setSettingValue(cutoffTime.toString());
        setting.setDescription("Cutoff time for " + mealType.name().toLowerCase() + " bookings");
        
        systemSettingsRepository.save(setting);
    }
    
    private String getSettingKeyForMealType(com.hostel.mess.entity.MealType mealType) {
        return switch (mealType) {
            case BREAKFAST -> SystemSettings.BREAKFAST_CUTOFF_KEY;
            case LUNCH -> SystemSettings.LUNCH_CUTOFF_KEY;
            case DINNER -> SystemSettings.DINNER_CUTOFF_KEY;
        };
    }
}

