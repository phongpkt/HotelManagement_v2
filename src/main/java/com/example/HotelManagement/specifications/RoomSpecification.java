package com.example.HotelManagement.specifications;

import com.example.HotelManagement.enums.RoomStatus;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RoomSpecification {
    public static Specification<Room> hasStatus(RoomStatus status) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("Status"), status);
        });
    }

    public static Specification<Room> hasHotel(Hotel hotel) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("hotel"), hotel);
        });
    }

    public static Specification<Room> hasType(RoomType type) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("type"), type);
        });
    }
}
