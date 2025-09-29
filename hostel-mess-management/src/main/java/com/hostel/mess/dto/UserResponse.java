package com.hostel.mess.dto;

import com.hostel.mess.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}

