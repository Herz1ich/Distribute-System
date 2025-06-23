package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.model.UsageEntry;
import org.example.springbootrestapi.model.CurrentPercentage;
import org.example.springbootrestapi.repository.UsageEntryRepository;
import org.example.springbootrestapi.repository.CurrentPercentageRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/energy")
@CrossOrigin
public class EnergyController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);

    private final UsageEntryRepository usageRepo;
    private final CurrentPercentageRepository percentageRepo;

    public EnergyController(UsageEntryRepository usageRepo, CurrentPercentageRepository percentageRepo) {
        this.usageRepo = usageRepo;
        this.percentageRepo = percentageRepo;
    }

    @GetMapping("/current")
    public List<CurrentPercentage> getCurrentPercentage() {
        return percentageRepo.findTop5ByOrderByTimestampDesc(); // 或者 findAll()
    }


    @GetMapping("/historical")
    public List<UsageEntry> getHistoricalUsage(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        logger.info("Received request: GET /energy/historical?start={} & end={}", start, end);
        return usageRepo.findByTimestampBetween(start, end);
    }
}
