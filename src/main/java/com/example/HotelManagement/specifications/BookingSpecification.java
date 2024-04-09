package com.example.HotelManagement.specifications;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Guest;
import com.example.HotelManagement.model.enums.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookingSpecification {
    public static Specification<Booking> hasStatus(BookingStatus status) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }
    public static Specification<Booking> hasGuest(Guest guest) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("guest"), guest);
        });
    }
}
