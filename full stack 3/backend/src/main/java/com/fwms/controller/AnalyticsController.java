package com.fwms.controller;

import com.fwms.dto.AnalyticsDto;
import com.fwms.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Analytics and reporting APIs")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    @Operation(summary = "Get analytics summary")
    public ResponseEntity<AnalyticsDto.SummaryResponse> getSummary() {
        AnalyticsDto.SummaryResponse summary = analyticsService.getSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/donation-status")
    @Operation(summary = "Get donation status distribution")
    public ResponseEntity<List<AnalyticsDto.ChartData>> getDonationStatusDistribution() {
        List<AnalyticsDto.ChartData> data = analyticsService.getDonationStatusDistribution();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/request-status")
    @Operation(summary = "Get request status distribution")
    public ResponseEntity<List<AnalyticsDto.ChartData>> getRequestStatusDistribution() {
        List<AnalyticsDto.ChartData> data = analyticsService.getRequestStatusDistribution();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user-roles")
    @Operation(summary = "Get user role distribution")
    public ResponseEntity<List<AnalyticsDto.ChartData>> getUserRoleDistribution() {
        List<AnalyticsDto.ChartData> data = analyticsService.getUserRoleDistribution();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/time-series")
    @Operation(summary = "Get time series data")
    public ResponseEntity<List<AnalyticsDto.TimeSeriesData>> getTimeSeriesData(
            @RequestParam(defaultValue = "30") int days) {
        List<AnalyticsDto.TimeSeriesData> data = analyticsService.getTimeSeriesData(days);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/top-food-types")
    @Operation(summary = "Get top food types")
    public ResponseEntity<List<AnalyticsDto.ChartData>> getTopFoodTypes() {
        List<AnalyticsDto.ChartData> data = analyticsService.getTopFoodTypes();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/top-locations")
    @Operation(summary = "Get top donation locations")
    public ResponseEntity<List<AnalyticsDto.ChartData>> getTopLocations() {
        List<AnalyticsDto.ChartData> data = analyticsService.getTopLocations();
        return ResponseEntity.ok(data);
    }
}