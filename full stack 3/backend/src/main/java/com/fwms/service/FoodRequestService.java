package com.fwms.service;

import com.fwms.dto.FoodRequestDto;
import com.fwms.model.FoodRequest;
import com.fwms.model.Donation;
import com.fwms.model.User;
import com.fwms.repository.FoodRequestRepository;
import com.fwms.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodRequestService {

    @Autowired
    private FoodRequestRepository foodRequestRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public FoodRequestDto.Response createRequest(FoodRequestDto.CreateRequest request) {
        User currentUser = authService.getCurrentUser();

        @SuppressWarnings("null")
        Donation donation = donationRepository.findById(request.getDonationId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (!donation.getStatus().equals(Donation.Status.AVAILABLE)) {
            throw new RuntimeException("Donation is not available for request");
        }

        if (request.getRequestedQuantity() > donation.getQuantity()) {
            throw new RuntimeException("Requested quantity exceeds available quantity");
        }

        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setRecipient(currentUser);
        foodRequest.setDonation(donation);
        foodRequest.setRequestedQuantity(request.getRequestedQuantity());
        foodRequest.setNotes(request.getNotes());

        foodRequest = foodRequestRepository.save(foodRequest);

        // Update donation status to REQUESTED
        donation.setStatus(Donation.Status.REQUESTED);
        donationRepository.save(donation);

        return mapToResponse(foodRequest);
    }

    public List<FoodRequestDto.Response> getAllRequests() {
        return foodRequestRepository.findAllOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FoodRequestDto.Response> getMyRequests() {
        User currentUser = authService.getCurrentUser();
        return foodRequestRepository.findByRecipient(currentUser).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FoodRequestDto.Response> getRequestsForMyDonations() {
        User currentUser = authService.getCurrentUser();
        return foodRequestRepository.findByDonor(currentUser).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public FoodRequestDto.Response getRequestById(Long id) {
        @SuppressWarnings("null")
        FoodRequest request = foodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapToResponse(request);
    }

    @SuppressWarnings("null")
    @Transactional
    public FoodRequestDto.Response updateRequestStatus(Long id, String status) {
        FoodRequest foodRequest = foodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User currentUser = authService.getCurrentUser();
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);
        boolean isDonor = foodRequest.getDonation().getDonor().getId().equals(currentUser.getId());

        if (!isAdmin && !isDonor) {
            throw new RuntimeException("You don't have permission to update this request");
        }

        FoodRequest.Status newStatus = FoodRequest.Status.valueOf(status.toUpperCase());
        foodRequest.setStatus(newStatus);

        // Update donation status based on request status
        Donation donation = foodRequest.getDonation();
        if (newStatus.equals(FoodRequest.Status.APPROVED)) {
            donation.setStatus(Donation.Status.APPROVED);
        } else if (newStatus.equals(FoodRequest.Status.REJECTED)) {
            donation.setStatus(Donation.Status.AVAILABLE);
        } else if (newStatus.equals(FoodRequest.Status.COMPLETED)) {
            donation.setStatus(Donation.Status.DELIVERED);
        }

        donationRepository.save(donation);
        foodRequest = foodRequestRepository.save(foodRequest);

        return mapToResponse(foodRequest);
    }

    @Transactional
    public void deleteRequest(Long id) {
        @SuppressWarnings("null")
        FoodRequest foodRequest = foodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User currentUser = authService.getCurrentUser();
        if (!foodRequest.getRecipient().getId().equals(currentUser.getId()) &&
            !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("You can only delete your own requests");
        }

        // Reset donation status if request is cancelled
        if (foodRequest.getStatus().equals(FoodRequest.Status.PENDING)) {
            Donation donation = foodRequest.getDonation();
            donation.setStatus(Donation.Status.AVAILABLE);
            donationRepository.save(donation);
        }

        foodRequestRepository.delete(foodRequest);
    }

    private FoodRequestDto.Response mapToResponse(FoodRequest request) {
        FoodRequestDto.Response response = new FoodRequestDto.Response();
        response.setId(request.getId());
        response.setRecipientId(request.getRecipient().getId());
        response.setRecipientName(request.getRecipient().getName());
        response.setDonationId(request.getDonation().getId());
        response.setFoodType(request.getDonation().getFoodType());
        response.setRequestedQuantity(request.getRequestedQuantity());
        response.setNotes(request.getNotes());
        response.setStatus(request.getStatus().name());
        response.setCreatedAt(request.getCreatedAt());
        response.setUpdatedAt(request.getUpdatedAt());
        return response;
    }
}