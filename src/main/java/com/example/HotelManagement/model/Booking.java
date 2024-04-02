package com.example.HotelManagement.model;

import com.example.HotelManagement.model.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @Column(name = "checkInDate")
    private Date checkInDate;
    @Column(name = "checkOutDate")
    private Date checkOutDate;
    @Column(name = "totalPrice")
    private double totalPrice;
    @Column(name = "status")
    private BookingStatus status;
    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Payment> payments;
}
