package org.example.currentpercentageservice.repository;

import org.example.currentpercentageservice.model.PercentageData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PercentageRepository extends JpaRepository<PercentageData, Long> {
    Optional<PercentageData> findByHour(LocalDateTime hour);
}

