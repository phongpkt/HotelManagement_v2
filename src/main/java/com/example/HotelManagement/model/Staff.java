package com.example.HotelManagement.model;

import com.example.HotelManagement.model.enums.StaffRole;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Staff")
public class Staff {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private long id;
    @ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
    @Column(name = "firstName")
    private String FirstName;
    @Column(name = "lastName")
    private String LastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private StaffRole role;
}
