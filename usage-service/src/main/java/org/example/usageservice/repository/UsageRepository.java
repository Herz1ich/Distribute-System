package org.example.usageservice.repository;

import org.example.usageservice.model.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsageRepository extends JpaRepository<UsageData, Long> {
    List<UsageData> findByHourBetween(LocalDateTime start, LocalDateTime end);
}
