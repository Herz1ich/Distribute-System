package org.example.usageservice.controller;

import org.example.usageservice.model.UsageData;
import org.example.usageservice.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class UsageController {

    @Autowired
    private UsageRepository repository;

    @GetMapping("/historical")
    public List<UsageData> getHistoricalData(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        System.out.println("🔥 UsageService 被调用了！start=" + start + " end=" + end);
        List<UsageData> result = repository.findByHourBetween(start, end);
        result.forEach(System.out::println);
        return result;
    }


}

