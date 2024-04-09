package com.example.HotelManagement.specifications;

import com.example.HotelManagement.model.Guest;
import org.springframework.data.jpa.domain.Specification;

public class GuestSpecification {
    public static Specification<Guest> hasFirstName(String firstName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("firstName"), firstName);
        });
    }
    public static Specification<Guest> hasLastName(String lastName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("lastName"), lastName);
        });
    }
}
