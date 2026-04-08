package com.fwms.controller;

import com.fwms.dto.DonationDto;
import com.fwms.service.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@Tag(name = "Donations", description = "Food donation management APIs")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    @Operation(summary = "Create a new food donation")
    public ResponseEntity<DonationDto.Response> createDonation(@Valid @RequestBody DonationDto.CreateRequest request) {
        DonationDto.Response response = donationService.createDonation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all donations")
    public ResponseEntity<List<DonationDto.Response>> getAllDonations() {
        List<DonationDto.Response> donations = donationService.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/available")
    @Operation(summary = "Get available donations for recipients")
    public ResponseEntity<List<DonationDto.Response>> getAvailableDonations(
            @RequestParam(required = false) String search) {
        List<DonationDto.Response> donations = donationService.getAvailableDonations(search);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/my")
    @Operation(summary = "Get current user's donations")
    public ResponseEntity<List<DonationDto.Response>> getMyDonations() {
        List<DonationDto.Response> donations = donationService.getMyDonations();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get donation by ID")
    public ResponseEntity<DonationDto.Response> getDonationById(@PathVariable Long id) {
        DonationDto.Response donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update donation")
    public ResponseEntity<DonationDto.Response> updateDonation(
            @PathVariable Long id,
            @Valid @RequestBody DonationDto.UpdateRequest request) {
        DonationDto.Response response = donationService.updateDonation(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete donation")
    public ResponseEntity<?> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.ok("Donation deleted successfully");
    }
}