package com.example.HotelManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
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
@Table(name = "RoomType")
public class RoomType {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "pricePerNight")
    private double pricePerNight;
    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    private List<Gallery> images;
    @OneToOne
    @JoinColumn(name = "preview_image_id")
    private Gallery previewImage;
    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private List<Room> rooms;
}
