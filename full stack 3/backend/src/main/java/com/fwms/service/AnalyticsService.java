package com.fwms.service;

import com.fwms.dto.AnalyticsDto;
import com.fwms.model.User;
import com.fwms.model.Donation;
import com.fwms.model.FoodRequest;
import com.fwms.model.Logistics;
import com.fwms.repository.UserRepository;
import com.fwms.repository.DonationRepository;
import com.fwms.repository.FoodRequestRepository;
import com.fwms.repository.LogisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private FoodRequestRepository foodRequestRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    public AnalyticsDto.SummaryResponse getSummary() {
        AnalyticsDto.SummaryResponse summary = new AnalyticsDto.SummaryResponse();

        // User statistics
        summary.setTotalUsers(userRepository.count());
        summary.setTotalDonors(userRepository.countByRole(User.Role.DONOR));
        summary.setTotalRecipients(userRepository.countByRole(User.Role.RECIPIENT));
        summary.setTotalAdmins(userRepository.countByRole(User.Role.ADMIN));
        summary.setTotalAnalysts(userRepository.countByRole(User.Role.ANALYST));

        // Donation statistics
        summary.setTotalDonations(donationRepository.count());
        summary.setAvailableDonations(donationRepository.countByStatus(Donation.Status.AVAILABLE));
        summary.setCompletedDonations(donationRepository.countByStatus(Donation.Status.DELIVERED));

        // Request statistics
        summary.setTotalRequests(foodRequestRepository.count());
        summary.setPendingRequests(foodRequestRepository.countByStatus(FoodRequest.Status.PENDING));
        summary.setApprovedRequests(foodRequestRepository.countByStatus(FoodRequest.Status.APPROVED));
        summary.setCompletedRequests(foodRequestRepository.countByStatus(FoodRequest.Status.COMPLETED));

        // Logistics statistics
        summary.setTotalLogistics(logisticsRepository.count());
        summary.setDeliveredLogistics(logisticsRepository.countByDeliveryStatus(Logistics.DeliveryStatus.DELIVERED));

        // Calculate total food saved (from completed donations)
        double totalFoodSaved = donationRepository.findByStatus(Donation.Status.DELIVERED)
                .stream()
                .mapToDouble(Donation::getQuantity)
                .sum();
        summary.setTotalFoodSaved(totalFoodSaved);

        return summary;
    }

    public List<AnalyticsDto.ChartData> getDonationStatusDistribution() {
        List<AnalyticsDto.ChartData> data = new ArrayList<>();
        for (Donation.Status status : Donation.Status.values()) {
            long count = donationRepository.countByStatus(status);
            data.add(new AnalyticsDto.ChartData(status.name(), count));
        }
        return data;
    }

    public List<AnalyticsDto.ChartData> getRequestStatusDistribution() {
        List<AnalyticsDto.ChartData> data = new ArrayList<>();
        for (FoodRequest.Status status : FoodRequest.Status.values()) {
            long count = foodRequestRepository.countByStatus(status);
            data.add(new AnalyticsDto.ChartData(status.name(), count));
        }
        return data;
    }

    public List<AnalyticsDto.ChartData> getUserRoleDistribution() {
        List<AnalyticsDto.ChartData> data = new ArrayList<>();
        for (User.Role role : User.Role.values()) {
            long count = userRepository.countByRole(role);
            data.add(new AnalyticsDto.ChartData(role.name(), count));
        }
        return data;
    }

    public List<AnalyticsDto.TimeSeriesData> getTimeSeriesData(int days) {
        List<AnalyticsDto.TimeSeriesData> data = new ArrayList<>();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // This would require custom queries to count by date ranges
            // For now, returning placeholder data
            long donations = 0; // Would need custom query
            long requests = 0;  // Would need custom query
            long deliveries = 0; // Would need custom query

            data.add(new AnalyticsDto.TimeSeriesData(date, donations, requests, deliveries));
        }

        return data;
    }

    public List<AnalyticsDto.ChartData> getTopFoodTypes() {
        // This would require a custom query to group by food type
        // For now, returning placeholder data
        List<AnalyticsDto.ChartData> data = new ArrayList<>();
        data.add(new AnalyticsDto.ChartData("Rice", 25));
        data.add(new AnalyticsDto.ChartData("Vegetables", 20));
        data.add(new AnalyticsDto.ChartData("Fruits", 15));
        data.add(new AnalyticsDto.ChartData("Bread", 12));
        data.add(new AnalyticsDto.ChartData("Dairy", 8));
        return data;
    }

    public List<AnalyticsDto.ChartData> getTopLocations() {
        // This would require a custom query to group by location
        // For now, returning placeholder data
        List<AnalyticsDto.ChartData> data = new ArrayList<>();
        data.add(new AnalyticsDto.ChartData("Downtown", 30));
        data.add(new AnalyticsDto.ChartData("Suburb A", 25));
        data.add(new AnalyticsDto.ChartData("Suburb B", 20));
        data.add(new AnalyticsDto.ChartData("City Center", 15));
        data.add(new AnalyticsDto.ChartData("Industrial Area", 10));
        return data;
    }
}