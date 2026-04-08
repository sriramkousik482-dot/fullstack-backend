package com.fwms.repository;

import com.fwms.model.Donation;
import com.fwms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonor(User donor);

    List<Donation> findByStatus(Donation.Status status);

    List<Donation> findByDonorAndStatus(User donor, Donation.Status status);

    @Query("SELECT d FROM Donation d WHERE d.status = 'AVAILABLE' AND d.expiryDate > :now ORDER BY d.createdAt DESC")
    List<Donation> findAvailableDonations(@Param("now") LocalDateTime now);

    @Query("SELECT d FROM Donation d WHERE d.status = 'AVAILABLE' AND d.expiryDate > :now AND " +
           "(LOWER(d.foodType) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.location) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Donation> findAvailableDonationsWithSearch(@Param("now") LocalDateTime now, @Param("search") String search);

    @Query("SELECT d FROM Donation d WHERE d.expiryDate <= :now AND d.status = 'AVAILABLE'")
    List<Donation> findExpiredDonations(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.status = :status")
    long countByStatus(@Param("status") Donation.Status status);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.donor = :donor")
    long countByDonor(@Param("donor") User donor);

    @Query("SELECT d FROM Donation d WHERE d.donor = :donor ORDER BY d.createdAt DESC")
    List<Donation> findByDonorOrderByCreatedAtDesc(@Param("donor") User donor);
}