package com.fwms.repository;

import com.fwms.model.FoodRequest;
import com.fwms.model.User;
import com.fwms.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {

    List<FoodRequest> findByRecipient(User recipient);

    List<FoodRequest> findByDonation(Donation donation);

    List<FoodRequest> findByStatus(FoodRequest.Status status);

    List<FoodRequest> findByRecipientAndStatus(User recipient, FoodRequest.Status status);

    List<FoodRequest> findByDonationAndStatus(Donation donation, FoodRequest.Status status);

    @Query("SELECT fr FROM FoodRequest fr WHERE fr.donation.donor = :donor")
    List<FoodRequest> findByDonor(@Param("donor") User donor);

    @Query("SELECT fr FROM FoodRequest fr WHERE fr.donation.donor = :donor AND fr.status = :status")
    List<FoodRequest> findByDonorAndStatus(@Param("donor") User donor, @Param("status") FoodRequest.Status status);

    @Query("SELECT COUNT(fr) FROM FoodRequest fr WHERE fr.status = :status")
    long countByStatus(@Param("status") FoodRequest.Status status);

    @Query("SELECT COUNT(fr) FROM FoodRequest fr WHERE fr.recipient = :recipient")
    long countByRecipient(@Param("recipient") User recipient);

    @Query("SELECT fr FROM FoodRequest fr ORDER BY fr.createdAt DESC")
    List<FoodRequest> findAllOrderByCreatedAtDesc();
}