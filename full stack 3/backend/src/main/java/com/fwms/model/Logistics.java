package com.fwms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logistics")
public class Logistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Food request is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private FoodRequest foodRequest;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Size(max = 255, message = "Pickup location must not exceed 255 characters")
    @Column(name = "pickup_location")
    private String pickupLocation;

    @Size(max = 255, message = "Delivery location must not exceed 255 characters")
    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Size(max = 100, message = "Driver name must not exceed 100 characters")
    @Column(name = "driver_name")
    private String driverName;

    @Size(max = 20, message = "Driver phone must not exceed 20 characters")
    @Column(name = "driver_phone")
    private String driverPhone;

    @Size(max = 20, message = "Vehicle number must not exceed 20 characters")
    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    @Column(length = 500)
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum DeliveryStatus {
        PENDING, ASSIGNED, PICKED_UP, IN_TRANSIT, DELIVERED, CANCELLED
    }

    // Constructors
    public Logistics() {}

    public Logistics(FoodRequest foodRequest, LocalDateTime pickupTime, String pickupLocation,
                    String deliveryLocation, String driverName, String driverPhone, String vehicleNumber) {
        this.foodRequest = foodRequest;
        this.pickupTime = pickupTime;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.vehicleNumber = vehicleNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodRequest getFoodRequest() {
        return foodRequest;
    }

    public void setFoodRequest(FoodRequest foodRequest) {
        this.foodRequest = foodRequest;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        this.updatedAt = LocalDateTime.now();
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}