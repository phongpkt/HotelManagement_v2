package com.example.HotelManagement.model;

import com.example.HotelManagement.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
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
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long id;
    @Column(name = "checkInDate")
    private Date checkInDate;
    @Column(name = "checkOutDate")
    private Date checkOutDate;
    @Column(name = "totalPrice")
    private double totalPrice;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Payment> payments;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
