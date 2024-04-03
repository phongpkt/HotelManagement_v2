package com.example.HotelManagement.model.dto;

import com.example.HotelManagement.model.enums.BookingStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class guestBookingDTO {
    private long room;
    private Date checkInDate;
    private Date checkOutDate;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private Date date_of_birth;
}
