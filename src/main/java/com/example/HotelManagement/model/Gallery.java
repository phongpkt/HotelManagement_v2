package com.example.HotelManagement.model;

import com.example.HotelManagement.enums.ImageTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    @Column(name = "image_url")
    private String image_url;
    @Column(name = "image_format")
    private String image_format;
    @Column(name = "image_type")
    @Enumerated(value = EnumType.STRING)
    private ImageTypes image_type;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    @JsonIgnore
    @Nullable
    private RoomType roomType;
}
