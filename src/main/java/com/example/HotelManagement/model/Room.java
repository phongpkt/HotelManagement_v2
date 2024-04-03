package com.example.HotelManagement.model;

import com.example.HotelManagement.model.enums.RoomStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Room")
public class Room implements Serializable {
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
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus Status;
    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Booking> bookings;
}
