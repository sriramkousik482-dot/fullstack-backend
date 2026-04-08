package com.fwms.controller;

import com.fwms.dto.FoodRequestDto;
import com.fwms.service.FoodRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Requests", description = "Food request management APIs")
public class RequestController {

    @Autowired
    private FoodRequestService foodRequestService;

    @PostMapping
    @Operation(summary = "Create a new food request")
    public ResponseEntity<FoodRequestDto.Response> createRequest(@Valid @RequestBody FoodRequestDto.CreateRequest request) {
        FoodRequestDto.Response response = foodRequestService.createRequest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all requests")
    public ResponseEntity<List<FoodRequestDto.Response>> getAllRequests() {
        List<FoodRequestDto.Response> requests = foodRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/my")
    @Operation(summary = "Get current user's requests")
    public ResponseEntity<List<FoodRequestDto.Response>> getMyRequests() {
        List<FoodRequestDto.Response> requests = foodRequestService.getMyRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/for-my-donations")
    @Operation(summary = "Get requests for current user's donations")
    public ResponseEntity<List<FoodRequestDto.Response>> getRequestsForMyDonations() {
        List<FoodRequestDto.Response> requests = foodRequestService.getRequestsForMyDonations();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get request by ID")
    public ResponseEntity<FoodRequestDto.Response> getRequestById(@PathVariable Long id) {
        FoodRequestDto.Response request = foodRequestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update request status")
    public ResponseEntity<FoodRequestDto.Response> updateRequestStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        FoodRequestDto.Response response = foodRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete request")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        foodRequestService.deleteRequest(id);
        return ResponseEntity.ok("Request deleted successfully");
    }
}