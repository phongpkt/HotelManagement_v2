package com.example.HotelManagement.specifications;

import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TypeSpecification {
    public static Specification<RoomType> hasName(String name) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        });
    }
    public static Specification<RoomType> hasPriceLessThan(Double price) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("pricePerNight"), price);
        });
    }
    public static Specification<RoomType> hasCapacityGreaterThan(Integer capacity) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity);
        });
    }
}
