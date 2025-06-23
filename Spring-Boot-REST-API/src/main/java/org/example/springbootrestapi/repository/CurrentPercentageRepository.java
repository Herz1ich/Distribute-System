package org.example.springbootrestapi.repository;

import org.example.springbootrestapi.model.CurrentPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CurrentPercentageRepository extends JpaRepository<CurrentPercentage, Long> {
    List<CurrentPercentage> findTop5ByOrderByTimestampDesc();
}

