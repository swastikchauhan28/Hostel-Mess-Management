package com.hostel.mess.controller;

import com.hostel.mess.dto.SystemSettingsRequest;
import com.hostel.mess.dto.UserResponse;
import com.hostel.mess.entity.MealType;
import com.hostel.mess.entity.Role;
import com.hostel.mess.entity.SystemSettings;
import com.hostel.mess.service.AdminService;
import com.hostel.mess.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private MealService mealService;
    
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Get list of all users in the system")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/role/{role}")
    @Operation(summary = "Get users by role", description = "Get users filtered by role")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Role role) {
        List<UserResponse> users = adminService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/users/{userId}/status")
    @Operation(summary = "Update user status", description = "Enable or disable a user account")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam boolean enabled) {
        UserResponse response = adminService.updateUserStatus(userId, enabled);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Delete user", description = "Delete a user from the system")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            adminService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/settings")
    @Operation(summary = "Get system settings", description = "Get all system settings")
    public ResponseEntity<List<SystemSettings>> getAllSystemSettings() {
        List<SystemSettings> settings = adminService.getAllSystemSettings();
        return ResponseEntity.ok(settings);
    }
    
    @GetMapping("/settings/{settingKey}")
    @Operation(summary = "Get specific setting", description = "Get a specific system setting by key")
    public ResponseEntity<SystemSettings> getSystemSetting(@PathVariable String settingKey) {
        SystemSettings setting = adminService.getSystemSetting(settingKey);
        return ResponseEntity.ok(setting);
    }
    
    @PutMapping("/settings")
    @Operation(summary = "Update system setting", description = "Update or create a system setting")
    public ResponseEntity<SystemSettings> updateSystemSetting(@Valid @RequestBody SystemSettingsRequest request) {
        SystemSettings setting = adminService.updateSystemSetting(request);
        return ResponseEntity.ok(setting);
    }
    
    @PutMapping("/settings/cutoff/{mealType}")
    @Operation(summary = "Update cutoff time", description = "Update cutoff time for a specific meal type")
    public ResponseEntity<?> updateCutoffTime(
            @PathVariable MealType mealType,
            @RequestParam String cutoffTime) {
        try {
            LocalTime time = LocalTime.parse(cutoffTime);
            adminService.updateCutoffTime(mealType, time);
            return ResponseEntity.ok("Cutoff time updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/meals")
    @Operation(summary = "Create meal", description = "Create a new meal for a specific date and type")
    public ResponseEntity<?> createMeal(
            @RequestParam MealType mealType,
            @RequestParam LocalDate mealDate) {
        try {
            mealService.createMeal(mealType, mealDate);
            return ResponseEntity.ok("Meal created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

