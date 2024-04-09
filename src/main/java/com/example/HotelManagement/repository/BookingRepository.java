package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    Booking getBookingById(Long id);
    @Query("SELECT b FROM Booking b JOIN b.guest g WHERE g.email = :email")
    List<Booking> findBookingByUserEmail(String email);

    @Query("SELECT b FROM Booking b WHERE b.checkOutDate <= CURRENT_DATE AND b.status = 'booked'")
    List<Booking> findByCheckOutDateBeforeAndStatus();
}
