package org.example.springbootrestapi.repository;

import org.example.springbootrestapi.model.UsageEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsageEntryRepository extends JpaRepository<UsageEntry, Long> {
    List<UsageEntry> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
