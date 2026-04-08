package com.fwms.service;

import com.fwms.dto.LogisticsDto;
import com.fwms.model.Logistics;
import com.fwms.model.User;
import com.fwms.repository.LogisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticsService {

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private AuthService authService;

    public List<LogisticsDto.Response> getMyTrackingOrders() {
        User currentUser = authService.getCurrentUser();
        return logisticsRepository.findByRecipient(currentUser).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LogisticsDto.Response getTrackingOrderById(Long id) {
        Logistics logistics = logisticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tracking order not found"));
        
        User currentUser = authService.getCurrentUser();
        if (!logistics.getFoodRequest().getRecipient().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to view this tracking order");
        }
        
        return mapToResponse(logistics);
    }

    private LogisticsDto.Response mapToResponse(Logistics logistics) {
        return new LogisticsDto.Response(
                logistics.getId(),
                logistics.getFoodRequest().getId(),
                logistics.getFoodRequest().getDonation().getFoodType(),
                logistics.getFoodRequest().getRequestedQuantity(),
                logistics.getFoodRequest().getDonation().getDonor().getName(),
                logistics.getPickupLocation(),
                logistics.getDeliveryLocation(),
                logistics.getPickupTime(),
                logistics.getDeliveryTime(),
                logistics.getDriverName(),
                logistics.getDriverPhone(),
                logistics.getVehicleNumber(),
                logistics.getDeliveryStatus().name(),
                logistics.getNotes(),
                logistics.getCreatedAt()
        );
    }
}
