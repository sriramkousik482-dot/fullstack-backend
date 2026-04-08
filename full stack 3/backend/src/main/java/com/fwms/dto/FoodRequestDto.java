package com.fwms.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class FoodRequestDto {

    public static class CreateRequest {
        @NotNull(message = "Donation ID is required")
        private Long donationId;

        @NotNull(message = "Requested quantity is required")
        @Min(value = 1, message = "Requested quantity must be at least 1")
        private Integer requestedQuantity;

        @Size(max = 500, message = "Notes must not exceed 500 characters")
        private String notes;

        public CreateRequest() {}

        // Getters and Setters
        public Long getDonationId() { return donationId; }
        public void setDonationId(Long donationId) { this.donationId = donationId; }
        public Integer getRequestedQuantity() { return requestedQuantity; }
        public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class UpdateRequest {
        @Min(value = 1, message = "Requested quantity must be at least 1")
        private Integer requestedQuantity;

        @Size(max = 500, message = "Notes must not exceed 500 characters")
        private String notes;

        private String status;

        public UpdateRequest() {}

        // Getters and Setters
        public Integer getRequestedQuantity() { return requestedQuantity; }
        public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Response {
        private Long id;
        private Long recipientId;
        private String recipientName;
        private Long donationId;
        private String foodType;
        private Integer requestedQuantity;
        private String notes;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getRecipientId() { return recipientId; }
        public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }
        public String getRecipientName() { return recipientName; }
        public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
        public Long getDonationId() { return donationId; }
        public void setDonationId(Long donationId) { this.donationId = donationId; }
        public String getFoodType() { return foodType; }
        public void setFoodType(String foodType) { this.foodType = foodType; }
        public Integer getRequestedQuantity() { return requestedQuantity; }
        public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}