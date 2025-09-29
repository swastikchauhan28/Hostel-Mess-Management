package com.hostel.mess.repository;

import com.hostel.mess.entity.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> {
    
    Optional<SystemSettings> findBySettingKey(String settingKey);
    
    boolean existsBySettingKey(String settingKey);
}

