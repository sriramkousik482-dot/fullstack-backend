package com.fwms.service;

import com.fwms.dto.DonationDto;
import com.fwms.model.Donation;
import com.fwms.model.User;
import com.fwms.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public DonationDto.Response createDonation(DonationDto.CreateRequest request) {
        User currentUser = authService.getCurrentUser();

        Donation donation = new Donation();
        donation.setDonor(currentUser);
        donation.setFoodType(request.getFoodType());
        donation.setQuantity(request.getQuantity());
        donation.setUnit(request.getUnit());
        donation.setExpiryDate(request.getExpiryDate());
        donation.setDescription(request.getDescription());
        donation.setLocation(request.getLocation());

        donation = donationRepository.save(donation);
        return mapToResponse(donation);
    }

    public List<DonationDto.Response> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DonationDto.Response> getAvailableDonations(String search) {
        List<Donation> donations;
        if (search != null && !search.trim().isEmpty()) {
            donations = donationRepository.findAvailableDonationsWithSearch(LocalDateTime.now(), search);
        } else {
            donations = donationRepository.findAvailableDonations(LocalDateTime.now());
        }
        return donations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DonationDto.Response> getMyDonations() {
        User currentUser = authService.getCurrentUser();
        return donationRepository.findByDonorOrderByCreatedAtDesc(currentUser).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DonationDto.Response getDonationById(Long id) {
        @SuppressWarnings("null")
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        return mapToResponse(donation);
    }

    @Transactional
    public DonationDto.Response updateDonation(Long id, DonationDto.UpdateRequest request) {
        @SuppressWarnings("null")
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        User currentUser = authService.getCurrentUser();
        if (!donation.getDonor().getId().equals(currentUser.getId()) &&
            !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("You can only update your own donations");
        }

        if (request.getFoodType() != null) donation.setFoodType(request.getFoodType());
        if (request.getQuantity() != null) donation.setQuantity(request.getQuantity());
        if (request.getUnit() != null) donation.setUnit(request.getUnit());
        if (request.getExpiryDate() != null) donation.setExpiryDate(request.getExpiryDate());
        if (request.getDescription() != null) donation.setDescription(request.getDescription());
        if (request.getLocation() != null) donation.setLocation(request.getLocation());
        if (request.getStatus() != null) donation.setStatus(Donation.Status.valueOf(request.getStatus()));

        donation = donationRepository.save(donation);
        return mapToResponse(donation);
    }

    @Transactional
    public void deleteDonation(Long id) {
        @SuppressWarnings("null")
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        User currentUser = authService.getCurrentUser();
        if (!donation.getDonor().getId().equals(currentUser.getId()) &&
            !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("You can only delete your own donations");
        }

        donationRepository.delete(donation);
    }

    @Transactional
    public void updateExpiredDonations() {
        List<Donation> expiredDonations = donationRepository.findExpiredDonations(LocalDateTime.now());
        for (Donation donation : expiredDonations) {
            donation.setStatus(Donation.Status.EXPIRED);
            donationRepository.save(donation);
        }
    }

    private DonationDto.Response mapToResponse(Donation donation) {
        DonationDto.Response response = new DonationDto.Response();
        response.setId(donation.getId());
        response.setDonorId(donation.getDonor().getId());
        response.setDonorName(donation.getDonor().getName());
        response.setFoodType(donation.getFoodType());
        response.setQuantity(donation.getQuantity());
        response.setUnit(donation.getUnit());
        response.setExpiryDate(donation.getExpiryDate());
        response.setDescription(donation.getDescription());
        response.setLocation(donation.getLocation());
        response.setStatus(donation.getStatus().name());
        response.setCreatedAt(donation.getCreatedAt());
        response.setUpdatedAt(donation.getUpdatedAt());
        return response;
    }
}