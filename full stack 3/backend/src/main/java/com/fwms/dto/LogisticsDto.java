package com.fwms.dto;

import java.time.LocalDateTime;

public class LogisticsDto {

    public static class Response {
        private Long id;
        private Long requestId;
        private String foodType;
        private Integer requestedQuantity;
        private String donorName;
        private String pickupLocation;
        private String deliveryLocation;
        private LocalDateTime pickupTime;
        private LocalDateTime deliveryTime;
        private String driverName;
        private String driverPhone;
        private String vehicleNumber;
        private String deliveryStatus;
        private String notes;
        private LocalDateTime createdAt;

        public Response() {}

        public Response(Long id, Long requestId, String foodType, Integer requestedQuantity,
                       String donorName, String pickupLocation, String deliveryLocation,
                       LocalDateTime pickupTime, LocalDateTime deliveryTime, String driverName,
                       String driverPhone, String vehicleNumber, String deliveryStatus,
                       String notes, LocalDateTime createdAt) {
            this.id = id;
            this.requestId = requestId;
            this.foodType = foodType;
            this.requestedQuantity = requestedQuantity;
            this.donorName = donorName;
            this.pickupLocation = pickupLocation;
            this.deliveryLocation = deliveryLocation;
            this.pickupTime = pickupTime;
            this.deliveryTime = deliveryTime;
            this.driverName = driverName;
            this.driverPhone = driverPhone;
            this.vehicleNumber = vehicleNumber;
            this.deliveryStatus = deliveryStatus;
            this.notes = notes;
            this.createdAt = createdAt;
        }

        // Getters
        public Long getId() { return id; }
        public Long getRequestId() { return requestId; }
        public String getFoodType() { return foodType; }
        public Integer getRequestedQuantity() { return requestedQuantity; }
        public String getDonorName() { return donorName; }
        public String getPickupLocation() { return pickupLocation; }
        public String getDeliveryLocation() { return deliveryLocation; }
        public LocalDateTime getPickupTime() { return pickupTime; }
        public LocalDateTime getDeliveryTime() { return deliveryTime; }
        public String getDriverName() { return driverName; }
        public String getDriverPhone() { return driverPhone; }
        public String getVehicleNumber() { return vehicleNumber; }
        public String getDeliveryStatus() { return deliveryStatus; }
        public String getNotes() { return notes; }
        public LocalDateTime getCreatedAt() { return createdAt; }

        // Setters
        public void setId(Long id) { this.id = id; }
        public void setRequestId(Long requestId) { this.requestId = requestId; }
        public void setFoodType(String foodType) { this.foodType = foodType; }
        public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
        public void setDonorName(String donorName) { this.donorName = donorName; }
        public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
        public void setDeliveryLocation(String deliveryLocation) { this.deliveryLocation = deliveryLocation; }
        public void setPickupTime(LocalDateTime pickupTime) { this.pickupTime = pickupTime; }
        public void setDeliveryTime(LocalDateTime deliveryTime) { this.deliveryTime = deliveryTime; }
        public void setDriverName(String driverName) { this.driverName = driverName; }
        public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }
        public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
        public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }
        public void setNotes(String notes) { this.notes = notes; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}
