package com.fwms.controller;

import com.fwms.dto.LogisticsDto;
import com.fwms.service.LogisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
@Tag(name = "Logistics", description = "Order tracking and logistics APIs")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @GetMapping("/my-tracking")
    @Operation(summary = "Get all tracking orders for current user")
    public ResponseEntity<List<LogisticsDto.Response>> getMyTrackingOrders() {
        List<LogisticsDto.Response> orders = logisticsService.getMyTrackingOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tracking order by ID")
    public ResponseEntity<LogisticsDto.Response> getTrackingOrderById(@PathVariable Long id) {
        LogisticsDto.Response order = logisticsService.getTrackingOrderById(id);
        return ResponseEntity.ok(order);
    }
}
