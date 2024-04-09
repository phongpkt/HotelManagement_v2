package com.example.HotelManagement.specifications;

import com.example.HotelManagement.model.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class HotelSpecification {
    public static Specification<Hotel> hasName(String name) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        });
    }
}
