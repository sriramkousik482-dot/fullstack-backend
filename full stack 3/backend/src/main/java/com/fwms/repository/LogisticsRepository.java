package com.fwms.repository;

import com.fwms.model.Logistics;
import com.fwms.model.FoodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogisticsRepository extends JpaRepository<Logistics, Long> {

    Optional<Logistics> findByFoodRequest(FoodRequest foodRequest);

    List<Logistics> findByDeliveryStatus(Logistics.DeliveryStatus deliveryStatus);

    @Query("SELECT l FROM Logistics l WHERE l.foodRequest.donation.donor = :donor")
    List<Logistics> findByDonor(@Param("donor") com.fwms.model.User donor);

    @Query("SELECT l FROM Logistics l WHERE l.foodRequest.recipient = :recipient")
    List<Logistics> findByRecipient(@Param("recipient") com.fwms.model.User recipient);

    @Query("SELECT COUNT(l) FROM Logistics l WHERE l.deliveryStatus = :status")
    long countByDeliveryStatus(@Param("status") Logistics.DeliveryStatus status);

    @Query("SELECT l FROM Logistics l ORDER BY l.createdAt DESC")
    List<Logistics> findAllOrderByCreatedAtDesc();
}