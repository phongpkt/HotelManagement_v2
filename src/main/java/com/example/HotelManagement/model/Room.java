package com.example.HotelManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;
    @ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name="type_id", nullable=false)
    private RoomType type;
    @Column(name = "status") //Available, Not Available
    private String Status;
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
