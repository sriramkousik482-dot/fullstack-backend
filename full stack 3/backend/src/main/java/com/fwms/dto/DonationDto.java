package com.fwms.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class DonationDto {

    public static class CreateRequest {
        @NotBlank(message = "Food type is required")
        @Size(max = 100, message = "Food type must not exceed 100 characters")
        private String foodType;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        @NotBlank(message = "Unit is required")
        private String unit = "kg";

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        private LocalDateTime expiryDate;

        @Size(max = 500, message = "Description must not exceed 500 characters")
        private String description;

        @NotBlank(message = "Location is required")
        @Size(max = 255, message = "Location must not exceed 255 characters")
        private String location;

        public CreateRequest() {}

        // Getters and Setters
        public String getFoodType() { return foodType; }
        public void setFoodType(String foodType) { this.foodType = foodType; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
        public LocalDateTime getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }

    public static class UpdateRequest {
        @Size(max = 100, message = "Food type must not exceed 100 characters")
        private String foodType;

        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        private String unit;

        @Future(message = "Expiry date must be in the future")
        private LocalDateTime expiryDate;

        @Size(max = 500, message = "Description must not exceed 500 characters")
        private String description;

        @Size(max = 255, message = "Location must not exceed 255 characters")
        private String location;

        private String status;

        public UpdateRequest() {}

        // Getters and Setters
        public String getFoodType() { return foodType; }
        public void setFoodType(String foodType) { this.foodType = foodType; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
        public LocalDateTime getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Response {
        private Long id;
        private Long donorId;
        private String donorName;
        private String foodType;
        private Integer quantity;
        private String unit;
        private LocalDateTime expiryDate;
        private String description;
        private String location;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getDonorId() { return donorId; }
        public void setDonorId(Long donorId) { this.donorId = donorId; }
        public String getDonorName() { return donorName; }
        public void setDonorName(String donorName) { this.donorName = donorName; }
        public String getFoodType() { return foodType; }
        public void setFoodType(String foodType) { this.foodType = foodType; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
        public LocalDateTime getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}