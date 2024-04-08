package com.example.HotelManagement.specifications;

import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.enums.RoomStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RoomSpecification {
    public static Specification<Room> hasStatus(RoomStatus status) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("Status"), status);
        });
    }

    public static Specification<Room> hasHotel(Long hotel_id) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("hotel"), hotel_id);
        });
    }

    public static Specification<Room> hasType(Long type_id) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("type"), type_id);
        });
    }
}
