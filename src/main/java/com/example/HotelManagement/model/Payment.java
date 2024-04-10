package com.example.HotelManagement.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payment")
public class Payment {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    @Column(name = "totalAmount")
    private double totalAmount;
    @Column(name = "payment_date")
    private Date payment_date;
    @Column(name = "payment_method")
    private String payment_method;
}
