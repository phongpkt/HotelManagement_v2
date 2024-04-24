package com.example.HotelManagement.dto;

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

    private String fullName;
    private String phone;
    private String email;
}
