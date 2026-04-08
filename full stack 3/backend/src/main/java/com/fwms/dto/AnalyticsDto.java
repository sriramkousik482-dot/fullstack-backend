package com.fwms.dto;

import java.time.LocalDate;

public class AnalyticsDto {

    public static class SummaryResponse {
        private long totalUsers;
        private long totalDonors;
        private long totalRecipients;
        private long totalAdmins;
        private long totalAnalysts;
        private long totalDonations;
        private long availableDonations;
        private long completedDonations;
        private long totalRequests;
        private long pendingRequests;
        private long approvedRequests;
        private long completedRequests;
        private long totalLogistics;
        private long deliveredLogistics;
        private double totalFoodSaved; // in kg

        public SummaryResponse() {}

        // Getters and Setters
        public long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
        public long getTotalDonors() { return totalDonors; }
        public void setTotalDonors(long totalDonors) { this.totalDonors = totalDonors; }
        public long getTotalRecipients() { return totalRecipients; }
        public void setTotalRecipients(long totalRecipients) { this.totalRecipients = totalRecipients; }
        public long getTotalAdmins() { return totalAdmins; }
        public void setTotalAdmins(long totalAdmins) { this.totalAdmins = totalAdmins; }
        public long getTotalAnalysts() { return totalAnalysts; }
        public void setTotalAnalysts(long totalAnalysts) { this.totalAnalysts = totalAnalysts; }
        public long getTotalDonations() { return totalDonations; }
        public void setTotalDonations(long totalDonations) { this.totalDonations = totalDonations; }
        public long getAvailableDonations() { return availableDonations; }
        public void setAvailableDonations(long availableDonations) { this.availableDonations = availableDonations; }
        public long getCompletedDonations() { return completedDonations; }
        public void setCompletedDonations(long completedDonations) { this.completedDonations = completedDonations; }
        public long getTotalRequests() { return totalRequests; }
        public void setTotalRequests(long totalRequests) { this.totalRequests = totalRequests; }
        public long getPendingRequests() { return pendingRequests; }
        public void setPendingRequests(long pendingRequests) { this.pendingRequests = pendingRequests; }
        public long getApprovedRequests() { return approvedRequests; }
        public void setApprovedRequests(long approvedRequests) { this.approvedRequests = approvedRequests; }
        public long getCompletedRequests() { return completedRequests; }
        public void setCompletedRequests(long completedRequests) { this.completedRequests = completedRequests; }
        public long getTotalLogistics() { return totalLogistics; }
        public void setTotalLogistics(long totalLogistics) { this.totalLogistics = totalLogistics; }
        public long getDeliveredLogistics() { return deliveredLogistics; }
        public void setDeliveredLogistics(long deliveredLogistics) { this.deliveredLogistics = deliveredLogistics; }
        public double getTotalFoodSaved() { return totalFoodSaved; }
        public void setTotalFoodSaved(double totalFoodSaved) { this.totalFoodSaved = totalFoodSaved; }
    }

    public static class ChartData {
        private String label;
        private long value;

        public ChartData() {}

        public ChartData(String label, long value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public long getValue() { return value; }
        public void setValue(long value) { this.value = value; }
    }

    public static class TimeSeriesData {
        private LocalDate date;
        private long donations;
        private long requests;
        private long deliveries;

        public TimeSeriesData() {}

        public TimeSeriesData(LocalDate date, long donations, long requests, long deliveries) {
            this.date = date;
            this.donations = donations;
            this.requests = requests;
            this.deliveries = deliveries;
        }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public long getDonations() { return donations; }
        public void setDonations(long donations) { this.donations = donations; }
        public long getRequests() { return requests; }
        public void setRequests(long requests) { this.requests = requests; }
        public long getDeliveries() { return deliveries; }
        public void setDeliveries(long deliveries) { this.deliveries = deliveries; }
    }
}