package com.energy.RestAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnergyController {

    @GetMapping("/energy/current")
    public Map<String, Object> getCurrentPercentage() {
        return Map.of(
                "community_depleted", 78.54,
                "grid_portion", 21.46
        );
    }

    @GetMapping("/energy/historical")
    public ResponseEntity<Map<String, Object>> getHistoricalData(
            @RequestParam String start,
            @RequestParam String end) {

        Map<String, Object> mockData = new HashMap<>();
        mockData.put("start", start);
        mockData.put("end", end);
        mockData.put("communityProduced", 143.024);
        mockData.put("communityUsed", 130.101);
        mockData.put("gridUsed", 14.75);

        return ResponseEntity.ok(mockData);
    }
}



