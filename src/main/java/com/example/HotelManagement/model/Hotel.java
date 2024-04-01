package com.example.HotelManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String Address;
    @Column(name = "phone_number")
    private String Phone;
    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Room> rooms;
    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Staff> staffs;
}
